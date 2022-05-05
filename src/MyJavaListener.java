import org.antlr.v4.runtime.*;


public class MyJavaListener extends JavaParserBaseListener{
    TokenStreamRewriter rewriter;
    TokenStreamRewriter rewriter2;
    String block, alter;
    int block_index = 0;

    public MyJavaListener(TokenStream tokens){
        rewriter = new TokenStreamRewriter(tokens); // 
        rewriter2 =  new TokenStreamRewriter(tokens) ; //
    }


    public TokenStreamRewriter getRewriter2(){
        return  rewriter2 ;
    }

    @Override
    public void enterStatement(JavaParser.StatementContext ctx) {
        switch (ctx.start.getText()){
            case "while":
                block = String.format("\"block_%d\"", block_index);
                alter = "\n\t\tSystem.out.println("+block+");";
                if(ctx.getChild(2).getText().charAt(0) =='{') {
                    rewriter.insertAfter(ctx.whileS.getStart(), alter);
                }else{
                    rewriter.insertBefore(ctx.whileS.getStart(), '{');
                    rewriter.insertAfter(ctx.whileS.getStop(), alter+"\n\t\t}");
                }
                preInserter(ctx, "block_", block_index);
                block_index++;
                break;
            case "for":
                block = String.format("\"block_%d\"", block_index);
                alter = "\n\t\tSystem.out.println("+block+");";
                if(ctx.getChild(4).getText().charAt(0) =='{') {
                    rewriter.insertAfter(ctx.forS.getStart(), alter);
                }else{
                    rewriter.insertBefore(ctx.forS.getStart(), '{');

                    rewriter.insertAfter(ctx.forS.getStop(), alter+"\n\t\t}");
                }
                preInserter(ctx, "block_", block_index);
                block_index++;
                break;
            case "do":
                block = String.format("\"block_%d\"", block_index);
                alter = "\n\t\tSystem.out.println("+block+");";
                if(ctx.getChild(1).getText().charAt(0) =='{') {
                    rewriter.insertAfter(ctx.doS.getStart(), alter);
                }else{
                    rewriter.insertAfter(ctx.getStart(), '{');
                    rewriter.insertAfter(ctx.doS.getStop(), alter+"\n\t\t}");
                }
                preInserter(ctx, "block_", block_index);
                block_index++;
                break;
            case "if":
                block = String.format("\"block_%d\"", block_index);
                alter = "\n\t\tSystem.out.println("+block+");";
                if(ctx.getChild(2).getText().charAt(0) =='{') {
                    rewriter.insertAfter(ctx.ifS.getStart(), alter);
                }else{
                    rewriter.insertBefore(ctx.ifS.getStart(), '{');
                    rewriter.insertAfter(ctx.ifS.getStop(), alter+"\n\t\t}");
                }
                preInserter(ctx, "block_", block_index);
                block_index++;
                break;
            case "switch":
                block = String.format("\"block_%d\"", block_index);
                alter = "\n\t\tSystem.out.println("+block+");";
                rewriter.insertBefore(ctx.getStop(), alter);
                preInserter(ctx, "block_", block_index);
                block_index++;
                break;
            case "try":
                block = String.format("\"block_%d\"", block_index);
                alter = "\n\t\tSystem.out.println("+block+");";
                rewriter.insertAfter(ctx.tryB.start, alter);
                preInserter(ctx, "block_", block_index);
                block_index++;
            default:
                ;
        }
        super.enterStatement(ctx);
    }

    @Override
    public void enterCatchClause(JavaParser.CatchClauseContext ctx) {
        block = String.format("\"block_%d\"", block_index);
        alter = "\n\t\tSystem.out.println("+block+");";
        rewriter.insertAfter(ctx.catchB.getStart(), alter);
        preInserter(ctx, "block_", block_index);
        block_index++;
        super.enterCatchClause(ctx);
    }

    @Override
    public void enterFinallyBlock(JavaParser.FinallyBlockContext ctx) {
        block = String.format("\"block_%d\"", block_index);
        alter = "\n\t\tSystem.out.println("+block+");";
        rewriter.insertAfter(ctx.block().getStart(), alter);
        preInserter(ctx, "block_", block_index);
        block_index++;
        super.enterFinallyBlock(ctx);
    }

    @Override
    public void enterElseStatement(JavaParser.ElseStatementContext ctx) {

        block = String.format("\"block_%d\"", block_index);
        alter = "\n\t\tSystem.out.println("+block+");";
        if(ctx.getChildCount()>0){




            if(!ctx.elseS.getStart().getText().equals("if")){
                if(ctx.getChild(1).getText().charAt(0) =='{') {
                    rewriter.insertAfter(ctx.elseS.getStart(), alter);

                }
                else{
                    rewriter.insertAfter(ctx.ELSE().getSymbol(), '{');
                    rewriter.insertAfter(ctx.elseS.getStop(), alter+"\n\t\t}");

                }
            }
            preInserter(ctx, "block_", block_index);
            block_index++;
        }


        super.enterElseStatement(ctx);
    }

    void preInserter(ParserRuleContext ctx, String s, int index){
        String html_div =" <pre id='"+s+index+"'>";
        rewriter2.insertBefore(ctx.start ,html_div);

        rewriter2.insertAfter(ctx.stop, "</pre>");
    }
}
