import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    final static private Booster booster =new Booster();
    final static private int LKH=100000;
    final static private int MLN=1000000;
    final static private int BLN = 1000000000;
    
    public static void main(String[] args) throws IOException {
        //  howToUse();
    }
    
    private static void howToUse() throws IOException{
        data <Integer>a=new data<>(0);
        write("enter integer value:", " "); read(a); write(a, "\n");    
        data <String>b=new data<>("");
        write("enter string value:", " "); read(b); write(b, "\n");
        data <Double>c=new data<>(0.0);
        write("enter double value:", " "); read(c); write(c, "\n");
        data <Long>d=new data<>(22L);
        write("enter long value:", " "); read(d); write(d, "\n");
        int arr[] = new int[5];
        write("enter five integers value:", " "); read(arr, 5); write(arr, 5, " "); // similarly for other primitives arrays
        write("", "\n");
    }
// Utils
    static class data<T>{ public T val; public data(T v){val=v;} public String toString(){return String.valueOf(val);}} // wrapper for primitive data-type(not for their arrays) to operate with read & write
    @SuppressWarnings("unchecked")
    public static void read(data<?> d) throws IOException{ //to read only one value data type == not array
        if (d instanceof data){
            if( d.val instanceof Integer || int.class.isAssignableFrom( d.val.getClass() )) ((data<Integer>) d).val = booster.readInt();
            else if(d.val instanceof Double || double.class.isAssignableFrom( d.val.getClass() )) ((data<Double>) d).val = booster.readDouble();
            else if(d.val instanceof Long || long.class.isAssignableFrom( d.val.getClass() )) ((data<Long>) d).val = booster.readLong();
            else if( d.val instanceof String ) ((data<String>) d).val = booster.readString();
        }
    } 
    public static <T> void read(T d, int len) throws IOException{ // to read an array
        if("[I".equals(d.getClass().getName())){ for(int i=0; i<len; i++)((int[])d)[i]=booster.readInt(); }
        else if("[J".equals(d.getClass().getName())){ for(int i=0; i<len; i++)((long[])d)[i]=booster.readLong(); }
        else if("[D".equals(d.getClass().getName())){ for(int i=0; i<len; i++)((double[])d)[i]=booster.readDouble(); }
        else if("[Ljava.lang.String;".equals(d.getClass().getName())){ for(int i=0; i<len; i++)((String[])d)[i]=booster.readString(); }
    }
    public static <T> void write(T d, String end){ System.out.print(d + end); }    
    public static<T> void write(T d, int len, String end){ // write array of values
        if("[I".equals(d.getClass().getName())){ for(int i=0; i<len; i++)System.out.print(((int[])d)[i] + end);}
        else if("[D".equals(d.getClass().getName())){ for(int i=0; i<len; i++)System.out.print(((double[])d)[i] + end); }
        else if("[J".equals(d.getClass().getName())){ for(int i=0; i<len; i++)System.out.print(((long[])d)[i] + end); }
        else if("[Ljava.lang.String;".equals(d.getClass().getName())){ for(int i=0; i<len; i++)System.out.print(((String[])d)[i] + end); }
    }
    static class Booster{
        final private int BUFFER_CAPACITY=1<<16;
        final private int EOF=-1;
        private byte[] buffer;
        private int bp;//pointer for buffer array
        private int bufferLen;//total len of readed buffer
        private DataInputStream din=null;
        Booster(){ din = new DataInputStream(System.in); buffer=new byte[BUFFER_CAPACITY]; bufferLen=bp=0; }
        public void fillBuffer() throws IOException{ bufferLen=din.read(buffer, bp=0, BUFFER_CAPACITY); if (bufferLen==EOF)buffer[0]=EOF; }
        public byte readByte() throws IOException{ if(bp==bufferLen) fillBuffer(); return buffer[bp++]; }
        public int readInt() throws IOException{
            int res=0;
            byte read=readByte();
            while(read <= ' ') read=readByte();
            boolean isNeg = read=='-';
            if (isNeg) read=readByte();
            while(read >= '0' && read<='9'){ res = res*10 + read-'0'; read=readByte(); }
            return isNeg?-res:res;
        }
        public long readLong() throws IOException{
            long res=0;
            byte read=readByte();
            while(read <= ' ') read=readByte();
            boolean isNeg = read=='-';
            if (isNeg) read=readByte();
            while(read >= '0' && read<='9'){res = res*10 + read-'0'; read=readByte();}
            return isNeg?-res:res;
        }
        public double readDouble() throws IOException{
            double res=0, den=1;
            byte read = readByte();
            while(read<=' ') read=readByte();
            boolean isNeg = read=='-';
            if (isNeg) read=readByte();
            while(read>='0' && read<='9'){ res = res*10 + read-'0'; read=readByte(); }
            if (read=='.'){
                read=readByte();
                while(read>='0' && read<='9'){ res += (double)(read-'0') / (den*=10.0); read=readByte();}
            }
            return isNeg?-res:res;
        }
        public String readString() throws IOException{
            int lineLen=512;
            byte[] res=new byte[lineLen];
            int i=0;
            byte read=readByte();
            while(read <= ' ') read=readByte();
            while(read != EOF){
                if(read=='\n' || read==' ') break;
                if (i>=lineLen) res=Arrays.copyOf(res, lineLen<<=1);
                res[i++]=read;
                read=readByte();
            }
            return new String(res, 0, i);
        }
    }
}

