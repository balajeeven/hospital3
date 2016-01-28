package hospial;
import java.util.Properties;

import cascading.flow.Flow;
import cascading.flow.FlowDef;
import cascading.flow.FlowProcess;
import cascading.flow.hadoop2.Hadoop2MR1FlowConnector;
import cascading.operation.AssertionLevel;
import cascading.operation.BaseOperation;
import cascading.operation.FunctionCall;
import cascading.operation.OperationCall;
import cascading.operation.assertion.AssertSizeEquals;
import cascading.pipe.Each;
import cascading.pipe.Pipe;
import cascading.pipe.SubAssembly;
import cascading.property.AppProps;
import cascading.scheme.Scheme;
import cascading.scheme.hadoop.TextDelimited;
import cascading.tap.SinkMode;
import cascading.tap.Tap;
import cascading.tap.hadoop.Hfs;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;
import cascading.tuple.TupleEntry;

/**
 * Created by AniruddhaS on 1/28/2016.
 */

public class HospitalAnalysis3 {
    public static void main(String args[]){
        String sourcePath=args[0];
        String sinkPath=args[1];
        String trapPath=args[2];
        Properties properties=new Properties();
        AppProps.setApplicationJarClass(properties, HospitalAnalysis3.class);
        Fields fieldNames=
                new Fields("Sr No",
                        "Hospital Name",
                        "Zone",
                        "State",
                        "City",
                        "Address",
                        "Contact Person",
                        "Contact Number",
                        "Pincode",
                        "Area");
        //create Schemes
        Scheme sourceScheme=new TextDelimited(fieldNames,"\t");
        Scheme sinkScheme=new TextDelimited(true,"\t");
        Scheme trapScheme=new TextDelimited();

        //Taps
        Tap sourceTap=new Hfs(sourceScheme,sourcePath);
        Tap sinkTap=new Hfs(sinkScheme,sinkPath,SinkMode.REPLACE);
        Tap trapTap=new Hfs(trapScheme,trapPath);

        //Pipe operations
        Pipe sourcePipe=new Pipe("source");
        Pipe sourceFiltered=new Pipe("filteredPipe");
        Pipe outputPipe=new Pipe("outputPipe");
        //put assertions
        AssertSizeEquals equals=new AssertSizeEquals(10);
        sourceFiltered=new Each(sourcePipe, AssertionLevel.STRICT,equals);

        outputPipe=new Assembler(sourceFiltered,fieldNames);

        FlowDef flowDef=FlowDef.flowDef()
                .setName("Hospital")
                .addSource(sourcePipe,sourceTap)
                .addTailSink(outputPipe,sinkTap)
                .addTrap(sourceFiltered,trapTap);
        Flow mFlow=new Hadoop2MR1FlowConnector(properties).connect(flowDef);
        mFlow.complete();

    }
}

class Assembler extends SubAssembly{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public Assembler(){

    }
    public Assembler(Pipe inPipe,Fields fields){
        setPrevious(inPipe);
        inPipe=new Each(inPipe,new Fields("City"),new CityWiseFilter(),Fields.ALL);
        setTails(inPipe);
    }
}

class CityWiseFilter extends BaseOperation<Tuple> implements cascading.operation.Function<Tuple>{
    /**
     *
     */
    private static final long serialVersionUID = -2381635909078632398L;
    public void prepare(FlowProcess fp,OperationCall<Tuple> oc){
        oc.setContext(Tuple.size(1));
    }
    public void cleanup(FlowProcess fp,OperationCall<Tuple> oc){
        oc.setContext(null);
    }
    @Override
    public void operate(FlowProcess flowProcess, FunctionCall<Tuple> functionCall) {
        TupleEntry entry=functionCall.getArguments();
        Tuple result=new Tuple();
        String city=entry.getString(0);
        if(city.toString().equals("Jamshedpur")){
            result.addString(city);
            functionCall.getOutputCollector().add(result);
        }

    }
}