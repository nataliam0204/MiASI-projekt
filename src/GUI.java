import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import parser.*;
import converter.LatexVisitor;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Map;


public class GUI {

    public GUI() {
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

        JButton instructionButton = new JButton("Instrukcja");
        instructionButton.setBounds(750, 20, 120, 30);
        instructionButton.setBackground(new Color(0x3A3A3A));
        instructionButton.setForeground(textColor);

        JButton greekButton = new JButton("Symbole greckie");
        greekButton.setBounds(50, 140, 150, 30);
        greekButton.setBackground(buttonColor);
        greekButton.setForeground(textColor);



        // Panel do przechowywania obrazów
        JPanel iconPanel = new JPanel();
        iconPanel.setLayout(new BoxLayout(iconPanel, BoxLayout.Y_AXIS));

        confirmButton.addActionListener(e -> {
            try {
                String expr = inputField.getText();

// Reset panelu z ikonami
                iconPanel.removeAll();

// Parsowanie CAŁOŚCI jako prog
                CharStream cs = CharStreams.fromString(expr);
                MathExprLexer lexer = new MathExprLexer(cs);
                CommonTokenStream tokens = new CommonTokenStream(lexer);
                MathExprParser parser = new MathExprParser(tokens);
                ParseTree tree = parser.prog();
                LatexVisitor visitor = new LatexVisitor();
                String latex = visitor.visit(tree);

                StringBuilder combinedLatex = new StringBuilder();
// Dziel po \n - wcześniej było dzielone po "\\" który znajduje się w środku macierzy w latechu - dlatego nie chciały się rysować macierze. Musi tak zostać
                String[] latexLines = latex.split("\n"); // podział po LaTeXowym złamaniu linii

                //tak musi być do obrazka!!!
                for (String line : latexLines) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        combinedLatex.append(line).append(" \\\\\n");

                        TeXFormula formula = new TeXFormula(line);
                        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20f);
                        icon.setInsets(new Insets(5, 5, 5, 5));

                        BufferedImage image = new BufferedImage(
                                icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2 = image.createGraphics();
                        g2.setColor(Color.WHITE);
                        g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
                        icon.paintIcon(new JLabel(), g2, 0, 0);

                        JLabel newImageLabel = new JLabel(new ImageIcon(image));
                        iconPanel.add(newImageLabel);
                    }
                }

                JScrollPane scrollPane = new JScrollPane(iconPanel);
                scrollPane.setBounds(550, 220, 300, 150);
                frame.add(scrollPane);

// Wstawienie całościowego LaTeX-a do textarea
                latexOutput.setText(latex);
                frame.revalidate();

                System.out.println("Obrazy zostały wygenerowane i dodane do panelu.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Błąd w LaTeX: " + ex.getMessage());
            }
        });

        // Obsługa przycisku "Zapisz obraz"
        saveButton.addActionListener(e -> {
            try {
                // Pobieranie wszystkich generowanych obrazów
                if (iconPanel.getComponentCount() > 0) {
                    System.out.println("Znaleziono panel z obrazami.");
                    for (int i = 0; i < iconPanel.getComponentCount(); i++) {
                        JLabel generatedImageLabel = (JLabel) iconPanel.getComponent(i);
                        ImageIcon icon = (ImageIcon) generatedImageLabel.getIcon();
                        BufferedImage image = (BufferedImage) icon.getImage();

                        // Wybór ścieżki zapisu obrazu
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Wybierz miejsce zapisu pliku");
                        fileChooser.setSelectedFile(new File("rownanie" + (i + 1) + ".png"));
                        int userSelection = fileChooser.showSaveDialog(frame);

                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            File fileToSave = fileChooser.getSelectedFile();

                            // Upewniamy się, że plik ma rozszerzenie .png
                            if (!fileToSave.getName().endsWith(".png")) {
                                fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
                            }

                            // Zapisz obraz
                            try {
                                ImageIO.write(image, "PNG", fileToSave);
                                System.out.println("Zapisano obraz: " + fileToSave.getAbsolutePath());
                            } catch (IOException ioException) {
                                JOptionPane.showMessageDialog(frame, "Błąd zapisu obrazu: " + ioException.getMessage());
                            }
                        }
                    }
                } else {
                    System.out.println("Brak wygenerowanych obrazów do zapisania.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Błąd zapisu obrazów: " + ex.getMessage());
            }
        });

        fileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Wybierz plik tekstowy");

            int result = fileChooser.showOpenDialog(frame);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    // Wczytanie zawartości pliku jako String
                    String content = new String(java.nio.file.Files.readAllBytes(selectedFile.toPath()));
                    inputField.setText(content);
                    System.out.println("Załadowano plik: " + selectedFile.getAbsolutePath());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Błąd wczytywania pliku: " + ex.getMessage());
                }
            }
        });
        instructionButton.addActionListener(e -> {
            String message = "Instrukcja korzystania z konwertera:\n\n" +
                    "- Używaj operatorów:\n" +
                    "  +  dodawanie\n" +
                    "  -  odejmowanie\n" +
                    "  *  mnożenie\n" +
                    "  /  dzielenie (piętrowy ułamek)\n" +
                    "  :  dzielenie ukośnikowe (np. 1:2 = 1/2)\n" +
                    "  ^  potęgowanie\n" +
                    "  |x|  wartość bezwzględna\n\n" +
                    "- Funkcje obsługiwane: sqrt, log, ln, sin, cos, tan\n" +
                    "  np. sqrt(4), sin(x), log(10)\n\n" +
                    "- Greckie litery wybierz z palety znaków greckich lub wpisuj słownie:\n" +
                    "  alfa, beta, gamma, delta, epsilon, theta, lambda, pi, sigma, omega\n" +
                    "  (wielkie litery: Sigma, Gamma, Theta, ...)\n\n" +
                    "- Obsługiwane są macierze w nawiasach kwadratowych:\n" +
                    "  np. [1 2, 3 4] lub [a b c, d e f]\n\n" +
                    "- Obsługiwane są wielokropki:\n" +
                    "  ...  (poziomo),  :..  (pionowo),  \\..  (ukośnie)\n\n" +
                    "- Używaj nawiasów ( ) do grupowania wyrażeń, np. (a + b)^2\n\n" +
                    "- Nazwy zmiennych: dowolne litery, np. x, y, t lub słowa np. total\n\n" +
                    "- Kilka równań oddziel średnikami (;)\n\n" +
                    "Przykład:\n" +
                    "alfa + beta = gamma; sqrt(4) + log(10) >= x; [1 2; 3 4] * x = y";


            JOptionPane.showMessageDialog(frame, message, "Instrukcja", JOptionPane.INFORMATION_MESSAGE);
        });
        greekButton.addActionListener(e -> {
            JDialog greekDialog = new JDialog(frame, "Symbole greckie", false);
            greekDialog.setSize(500, 300);

            JPanel gridPanel = new JPanel();
            gridPanel.setLayout(new GridLayout(0, 6, 5, 5)); // dynamiczna liczba wierszy, 6 kolumn

            Map<String, String> greekLetters = Map.ofEntries(
                    Map.entry("alfa", "alpha"),
                    Map.entry("beta", "beta"),
                    Map.entry("gamma", "gamma"),
                    Map.entry("delta", "delta"),
                    Map.entry("epsilon", "epsilon"),
                    Map.entry("zeta", "zeta"),
                    Map.entry("eta", "eta"),
                    Map.entry("theta", "theta"),
                    Map.entry("iota", "iota"),
                    Map.entry("kappa", "kappa"),
                    Map.entry("lambda", "lambda"),
                    Map.entry("mu", "mu"),
                    Map.entry("nu", "nu"),
                    Map.entry("xi", "xi"),
                    Map.entry("omicron", "omicron"),
                    Map.entry("pi", "pi"),
                    Map.entry("rho", "rho"),
                    Map.entry("sigma", "sigma"),
                    Map.entry("tau", "tau"),
                    Map.entry("upsilon", "upsilon"),
                    Map.entry("phi", "phi"),
                    Map.entry("chi", "chi"),
                    Map.entry("psi", "psi"),
                    Map.entry("omega", "omega"),
                    Map.entry("Gamma", "Gamma"),
                    Map.entry("Delta", "Delta"),
                    Map.entry("Theta", "Theta"),
                    Map.entry("Lambda", "Lambda"),
                    Map.entry("Xi", "Xi"),
                    Map.entry("Pi", "Pi"),
                    Map.entry("Sigma", "Sigma"),
                    Map.entry("Upsilon", "Upsilon"),
                    Map.entry("Phi", "Phi"),
                    Map.entry("Psi", "Psi"),
                    Map.entry("Omega", "Omega")
            );

            List<String> greekOrder = List.of(
                    "alfa", "beta", "gamma", "delta", "epsilon", "zeta", "eta", "theta", "iota", "kappa",
                    "lambda", "mu", "nu", "xi", "omicron", "pi", "rho", "sigma", "tau", "upsilon",
                    "phi", "chi", "psi", "omega",
                    "Gamma", "Delta", "Theta", "Lambda", "Xi", "Pi", "Sigma", "Upsilon", "Phi", "Psi", "Omega"
            );

            for (String word : greekOrder) {
                String latex = greekLetters.get(word);

                TeXFormula formula = new TeXFormula("\\" + latex);
                TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 18f);
                icon.setInsets(new Insets(2, 2, 2, 2));

                JButton symbolButton = new JButton();
                symbolButton.setIcon(icon);
                symbolButton.setToolTipText(word);
                symbolButton.setFocusPainted(false);
                symbolButton.setMargin(new Insets(2, 2, 2, 2));
                symbolButton.setBackground(new Color(0x424242));  // ciemnoszary
                symbolButton.setForeground(Color.WHITE);          // biały tekst


                symbolButton.addActionListener(ae -> {
                    inputField.setText(inputField.getText() + word);
                });

                gridPanel.add(symbolButton);
            }



            JScrollPane scrollPane = new JScrollPane(gridPanel);
            greekDialog.add(scrollPane);
            greekDialog.setLocationRelativeTo(frame);
            greekDialog.setVisible(true);
        });




        frame.add(instructionButton);
        frame.add(labelInput);
        frame.add(inputField);
        frame.add(orLabel);
        frame.add(fileButton);
        frame.add(confirmButton);
        frame.add(latexOutput);
        frame.add(copyButton);
        frame.add(imageLabel);
        frame.add(saveButton);
        frame.add(greekButton);

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