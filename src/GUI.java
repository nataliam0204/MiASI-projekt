import be.ugent.caagt.jmathtex.TeXFormula;
import be.ugent.caagt.jmathtex.TeXIcon;
import be.ugent.caagt.jmathtex.TeXConstants;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import parser.*;
import converter.LatexVisitor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GUI {

    public GUI(){
        JFrame frame = new JFrame("LaTeX Expression Formatter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLayout(null);

        Color bgColor = new Color(0x595959);
        Color textColor = Color.WHITE;
        Color inputColor = new Color(0xE1E1E1);
        Color buttonColor = new Color(0x424242);
        Color buttonAcceptColor = new Color(0x2C2C2C);

        frame.getContentPane().setBackground(bgColor);

        JLabel labelInput = new JLabel("Wpisz wyrażenie tutaj");
        labelInput.setBounds(50, 50, 200, 25);
        labelInput.setForeground(textColor);

        JTextField inputField = new JTextField("(x+2)*y/4=2^(x)");
        inputField.setBounds(50, 80, 300, 30);
        inputField.setBackground(inputColor);
        inputField.setForeground(Color.BLACK);

        JLabel orLabel = new JLabel("lub");
        orLabel.setBounds(440, 85, 30, 20);
        orLabel.setForeground(textColor);

        JButton fileButton = new JButton("Szukaj");
        fileButton.setBounds(550, 80, 300, 30);
        fileButton.setBackground(buttonColor);
        fileButton.setForeground(textColor);

        JButton confirmButton = new JButton("Potwierdź");
        confirmButton.setBounds(300, 140, 300, 30);
        confirmButton.setBackground(buttonAcceptColor);
        confirmButton.setForeground(textColor);

        JTextArea latexOutput = new JTextArea("\\frac{(x+2) \\cdot y}{4} = 2^{x}");
        latexOutput.setBounds(50, 220, 300, 150);
        latexOutput.setFont(new Font("Monospaced", Font.PLAIN, 14));
        latexOutput.setBackground(inputColor);
        latexOutput.setForeground(Color.BLACK);

        JButton copyButton = new JButton("Skopiuj wyrażenie");
        copyButton.setBounds(50, 380, 300, 30);
        copyButton.setBackground(buttonColor);
        copyButton.setForeground(textColor);

        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(550, 220, 300, 150);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        imageLabel.setOpaque(true);
        imageLabel.setBackground(new Color(0xeeeeee));

        JButton saveButton = new JButton("Zapisz obraz");
        saveButton.setBounds(550, 380, 300, 30);
        saveButton.setBackground(buttonColor);
        saveButton.setForeground(textColor);

        confirmButton.addActionListener(e -> {
            try {
                String expr = inputField.getText();

                // PARSER + VISITOR
                CharStream cs = CharStreams.fromString(expr);
                MathExprLexer lexer = new MathExprLexer(cs);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                MathExprParser parser = new MathExprParser(tokens);
                ParseTree tree = parser.prog();
                LatexVisitor visitor = new LatexVisitor();
                String latex = visitor.visit(tree);

                // Wyświetl w polu tekstowym:
                latexOutput.setText(latex);
                latexOutput.setCaretPosition(0);

                // Render do obrazu:
                TeXFormula formula = new TeXFormula(latex);
                TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20f);
                icon.setInsets(new Insets(5, 5, 5, 5));

                BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = image.createGraphics();
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
                icon.paintIcon(new JLabel(), g2, 0, 0);
                imageLabel.setIcon(new ImageIcon(image));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Błąd w LaTeX: " + ex.getMessage());
            }
        });


        frame.add(labelInput);
        frame.add(inputField);
        frame.add(orLabel);
        frame.add(fileButton);
        frame.add(confirmButton);
        frame.add(latexOutput);
        frame.add(copyButton);
        frame.add(imageLabel);
        frame.add(saveButton);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Przykładowe wejście: układ równań z różnymi funkcjami
        String input = "(1 + 2) * sqrt(4); |x+1| = 3; sin(0) + log(10) >= y";

        // Tworzenie strumienia znaków z tekstu
        CharStream cs = CharStreams.fromString(input);

        // Tworzenie analizatora leksykalnego i parsera
        MathExprLexer lexer = new MathExprLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MathExprParser parser = new MathExprParser(tokens);

        // Uruchomienie parsera od reguły 'prog' (czyli całość)
        ParseTree tree = parser.prog();

        // Użycie odwiedzającego do wygenerowania LaTeX-a
        LatexVisitor visitor = new LatexVisitor();
        String latex = visitor.visit(tree);

        // Wyświetlenie wyniku
        System.out.println("LaTeX:\n" + latex);

        new GUI();
    }
}

/*
import org.antlr.v4.runtime.*;
        import org.antlr.v4.runtime.tree.*;
        import parser.*;
        import converter.LatexVisitor;

public class GUI {
    public static void main(String[] args) {
        // Przykładowe wejście — możesz potem zmienić na to, co wpisuje użytkownik
        String input = "(1 + 2) * sqrt(4)";

        // Tworzenie strumienia znaków i uruchomienie parsera
        CharStream cs = CharStreams.fromString(input);
        MathExprLexer lexer = new MathExprLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MathExprParser parser = new MathExprParser(tokens);
        ParseTree tree = parser.expr();

        // Uruchomienie odwiedzającego, który generuje kod LaTeX
        LatexVisitor visitor = new LatexVisitor();
        String latex = visitor.visit(tree);

        // Wypisanie kodu LaTeX
        System.out.println("LaTeX: " + latex);
    }
}
*/