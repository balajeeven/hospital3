package hospial;
import java.util.Properties;

import cascading.flow.Flow;
import cascading.flow.FlowDef;
adoop.Hfs;
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
