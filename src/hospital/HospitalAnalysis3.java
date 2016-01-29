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



class Assembler extends SubAssembly{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public Assembler(){

    }
    public Assembler(Pipe inPipe,Fields fields){
        setPrevious(inPipe);

        }

    }
}
