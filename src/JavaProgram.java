import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.*;
import java.util.*;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class JavaProgram {

    static String file_name ;
    static String java ;
    static String outdir ;

    public static  void main(String[] args) throws Exception {
        //to run all test files
                int i = 1;
                //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                while (i<4){
                    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                    processor("tests/t_"+i+".java",""+i);
                    i++;
                }
            }





    public static  MyJavaListener generateAndWalkParserTree(CharStream input ) {
        JavaLexer lexer = new JavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokens);
        ParseTree tree = parser.compilationUnit();

        ParseTreeWalker walker = new ParseTreeWalker();
        MyJavaListener extractor = new MyJavaListener(tokens);
        walker.walk(extractor, tree); // initiate walk of tree with listener

        return extractor ;
    }

    public static File  generateJavaInjectionCode (MyJavaListener extractor , String testNumber)
            throws IOException
    {

         file_name = "output_" + testNumber;
         java = file_name + ".java";
         outdir = "output/";

        // write the answer to file
        File JavaFile = new File(outdir + java);
        if (!JavaFile.createNewFile()) {
            boolean res = JavaFile.delete();
            JavaFile = new File(outdir + java);
        }
        FileWriter myWriter = new FileWriter(outdir + java);
        String javatext = extractor.rewriter.getText()
                .replace("t_", "output_") ;
        myWriter.write(javatext);
        myWriter.close();

        return  JavaFile ;
    }


    public static BufferedReader runCode (File JavaFile){

        Process theProcess = null;
        BufferedReader inStream = null;
        String file_name= JavaFile.getName() ;

        JavaFile.getParentFile().mkdirs();


        // Compile source file.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, System.out, null, JavaFile.getPath());
        try{
            theProcess = Runtime.getRuntime().exec("java output/"+file_name);
        }
        catch(IOException e){
            System.err.println("Error on exec() method");
            e.printStackTrace();
        }
        inStream = new BufferedReader(
                new InputStreamReader(theProcess.getInputStream()));

        return  inStream ;

    }

    public static void writefiles (BufferedReader  inStream ,MyJavaListener extractor) throws IOException {

        File HtmlFile = new File(outdir+file_name+".html") ;
        FileWriter myHtmlWriter = new FileWriter(outdir +file_name+".html" );

        myHtmlWriter.write( "<html> \n<head> \n<style>\n" +
                " body{background: chartreuse;}\n pre{background : lightsalmon}\n" ) ;
        String s = null;
        // write style of the code
        File blocks = new File(outdir+"block_"+file_name+".txt");
        FileWriter file = new FileWriter(outdir+"block_"+file_name+".txt" );
        String x = "";
        while ((s = inStream.readLine()) != null) {
            if(!s.matches(".+_[0-9]+"))
            {
                continue;
            }

            x+=s+"\n";

            System.out.println(s);
            s = "#"+s+"{background:chartreuse}\n" ;
            myHtmlWriter.write(s);
        }
        file.write(x);
        file.close();


        myHtmlWriter.write("</style>\n </head>\n <body>\n") ;
        myHtmlWriter.write(extractor.getRewriter2().getText().replace("\n", "<br>"));
        myHtmlWriter.write("</body> \n</html> \n") ;

        myHtmlWriter.close();


    }


    static void processor(String path, String testNum) throws IOException {
        CharStream input = CharStreams.fromFileName(path);

        MyJavaListener extractor =  generateAndWalkParserTree(input ) ;

        File JavaFile =  generateJavaInjectionCode(extractor , testNum) ;

        // run the file
        BufferedReader inStream = runCode (JavaFile) ;

        writefiles(inStream ,extractor) ;
    }




}
