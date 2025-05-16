import org.antlr.v4.runtime.*;

public class CustomErrorListener extends BaseErrorListener {
    private StringBuilder errorMessages = new StringBuilder();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg, RecognitionException e) {
        errorMessages.append("Błąd składniowy w linii ")
                .append(line)
                .append(", kolumna ")
                .append(charPositionInLine)
                .append(": ")
                .append(msg)
                .append("\n");
    }

    public boolean hasErrors() {
        return errorMessages.length() > 0;
    }

    public String getErrors() {
        return errorMessages.toString();
    }

    public void reset() {
        errorMessages.setLength(0);
    }
}
