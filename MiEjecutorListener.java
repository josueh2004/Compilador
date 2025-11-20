package Analizador;

import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

public class MiEjecutorListener extends AnalizadorLexicoBaseListener {
    private StringBuilder programOutput = new StringBuilder();
    private java.util.Map<String, Object> symbolTable = new java.util.HashMap<>();

    @Override
    public void enterMainDecl(AnalizadorLexicoParser.MainDeclContext ctx) {
        programOutput.append("Ejecutando main...\n");
    }

    @Override
    public void exitVarDecl(AnalizadorLexicoParser.VarDeclContext ctx) {
        String id = ctx.ID().getText();
        if (ctx.ASIGNACION() != null) {
            Object value = evaluateExpr(ctx.expr());
            symbolTable.put(id, value);
            programOutput.append("Declarada variable ").append(id).append(" = ").append(value).append("\n");
        } else {
            symbolTable.put(id, null);
            programOutput.append("Declarada variable ").append(id).append(" sin valor inicial\n");
        }
    }

    @Override
    public void exitAssignStmt(AnalizadorLexicoParser.AssignStmtContext ctx) {
        TerminalNode idNode = ctx.ID();
        String id = idNode.getText();
        java.util.List<AnalizadorLexicoParser.ExprContext> exprList = ctx.expr();
        Object value = (exprList != null && !exprList.isEmpty()) ? evaluateExpr(exprList.get(exprList.size() - 1)) : null;
        if (value != null) {
            symbolTable.put(id, value);
            programOutput.append("Asignado ").append(id).append(" = ").append(value).append("\n");
        }
    }

    @Override
    public void exitIncDecStmt(AnalizadorLexicoParser.IncDecStmtContext ctx) {
        String id = ctx.ID().getText();
        Object value = symbolTable.getOrDefault(id, 0);
        if (value instanceof Integer) {
            int newValue = (Integer) value;
            if (ctx.INCREMENTO() != null) {
                newValue++;
                programOutput.append("Incremento postfijo: ").append(id).append(" = ").append(newValue).append("\n");
            } else if (ctx.DECREMENTO() != null) {
                newValue--;
                programOutput.append("Decremento postfijo: ").append(id).append(" = ").append(newValue).append("\n");
            }
            symbolTable.put(id, newValue);
        }
    }

    @Override
    public void exitForStmt(AnalizadorLexicoParser.ForStmtContext ctx) {
        // Ejecutar varDecl (inicialización)
        ParseTreeWalker walker = new ParseTreeWalker();
        MiEjecutorListener innerListener = new MiEjecutorListener();
        walker.walk(innerListener, ctx.varDecl());

        // Condición del bucle
        while (true) {
            Object condition = evaluateExpr(ctx.expr());
            if (!(condition instanceof Integer) || (Integer) condition == 0) break;

            // Ejecutar el bloque
            walker.walk(innerListener, ctx.block());

            // Incremento o asignación opcional
            if (ctx.assignStmt() != null) {
                walker.walk(innerListener, ctx.assignStmt());
            } else if (ctx.incDecExpr() != null) {
                TerminalNode idNode = ctx.incDecExpr().ID();
                String id = idNode.getText();
                Object value = symbolTable.getOrDefault(id, 0);
                if (value instanceof Integer) {
                    int newValue = (Integer) value;
                    if (ctx.incDecExpr().INCREMENTO() != null) newValue++;
                    else if (ctx.incDecExpr().DECREMENTO() != null) newValue--;
                    symbolTable.put(id, newValue);
                }
            }
        }
    }

    @Override
    public void exitPrintStmt(AnalizadorLexicoParser.PrintStmtContext ctx) {
        if (ctx.CADENA() != null) {
            String text = ctx.CADENA().getText().replace("\"", "");
            programOutput.append(text).append("\n");
        } else {
            Object value = evaluateExpr(ctx.expr());
            if (value instanceof String) {
                programOutput.append(value).append("\n");
            } else {
                programOutput.append(value != null ? value : "null").append("\n");
            }
        }
    }

    @Override
    public void exitReadStmt(AnalizadorLexicoParser.ReadStmtContext ctx) {
        String id = ctx.ID().getText();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        programOutput.append("Ingresa valor para ").append(id).append(": ");
        int value = scanner.nextInt();
        symbolTable.put(id, value);
        programOutput.append(value).append("\n");
        scanner.close();
    }

    @Override
    public void exitReturnStmt(AnalizadorLexicoParser.ReturnStmtContext ctx) {
        if (ctx.expr() != null) {
            Object value = evaluateExpr(ctx.expr());
            programOutput.append("Retorno: ").append(value != null ? value : "null").append("\n");
            symbolTable.put("returnValue", value);
        }
    }

    @Override
    public void exitMethodCallStmt(AnalizadorLexicoParser.MethodCallStmtContext ctx) {
        String methodName = ctx.ID().getText();
        java.util.List<AnalizadorLexicoParser.ExprContext> params = ctx.expr();
        if (methodName.equals("metodoSuma") && params.size() == 2) {
            Object param1 = evaluateExpr(params.get(0));
            Object param2 = evaluateExpr(params.get(1));
            if (param1 instanceof Integer && param2 instanceof Integer) {
                int result = (Integer) param1 + (Integer) param2;
                symbolTable.put("returnValue", result);
                programOutput.append("Llamada a metodoSuma: ").append(result).append("\n");
            }
        } else if (methodName.equals("cicloFor") && params.size() == 1) {
            Object param = evaluateExpr(params.get(0));
            if (param instanceof Integer) {
                int limit = (Integer) param;
                for (int i = 0; i <= limit; i++) {
                    symbolTable.put("i", i);
                    programOutput.append("El valor de i es: ").append(i).append("\n");
                }
            }
        }
    }

    private Object evaluateExpr(AnalizadorLexicoParser.ExprContext ctx) {
        if (ctx == null) return null;
        if (ctx.ID() != null) {
            return symbolTable.getOrDefault(ctx.ID().getText(), 0);
        } else if (ctx.NUMERO() != null) {
            return Integer.parseInt(ctx.NUMERO().getText());
        } else if (ctx.CADENA() != null) {
            return ctx.CADENA().getText().replace("\"", "");
        } else if (ctx.INCREMENTO() != null || ctx.DECREMENTO() != null) {
            String id = ctx.ID().getText();
            Object value = symbolTable.getOrDefault(id, 0);
            if (value instanceof Integer) {
                int newValue = (Integer) value;
                if (ctx.INCREMENTO() != null) newValue++;
                else if (ctx.DECREMENTO() != null) newValue--;
                symbolTable.put(id, newValue);
                return newValue;
            }
        } else if (ctx.expr().size() == 2) {
            Object left = evaluateExpr(ctx.expr(0));
            Object right = evaluateExpr(ctx.expr(1));
            if (left instanceof String && right instanceof Integer) {
                return left + right.toString();
            } else if (left instanceof Integer && right instanceof Integer) {
                int leftVal = (Integer) left;
                int rightVal = (Integer) right;
                if (ctx.SUMA() != null) return leftVal + rightVal;
                if (ctx.RESTA() != null) return leftVal - rightVal;
                if (ctx.MULTIPLICACION() != null) return leftVal * rightVal;
                if (ctx.DIVISION() != null) return leftVal / rightVal;
                if (ctx.POTENCIA() != null) return (int) Math.pow(leftVal, rightVal);
                if (ctx.RESIDUO() != null) return leftVal % rightVal;
                if (ctx.MAYORQUE() != null) return leftVal > rightVal ? 1 : 0;
                if (ctx.MENORQUE() != null) return leftVal < rightVal ? 1 : 0;
                if (ctx.MAYORIGUAL() != null) return leftVal >= rightVal ? 1 : 0;
                if (ctx.MENORIGUAL() != null) return leftVal <= rightVal ? 1 : 0;
                if (ctx.IGUALIGUAL() != null) return leftVal == rightVal ? 1 : 0;
                if (ctx.AND() != null) return (leftVal != 0 && rightVal != 0) ? 1 : 0;
                if (ctx.OR() != null) return (leftVal != 0 || rightVal != 0) ? 1 : 0;
            }
        }
        return null;
    }

    private Object evaluateExpr(java.util.List<AnalizadorLexicoParser.ExprContext> exprList) {
        if (exprList == null || exprList.isEmpty()) return null;
        return evaluateExpr(exprList.get(exprList.size() - 1));
    }

    public String getOutput() {
        return programOutput.toString();
    }
}