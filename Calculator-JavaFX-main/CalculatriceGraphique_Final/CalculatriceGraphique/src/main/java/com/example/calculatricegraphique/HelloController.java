package com.example.calculatricegraphique;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import org.mariuszgromada.math.mxparser.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class HelloController {
    @FXML
    public TextArea champReponse;

    private String operation = "";
    private String resultat = "";

    /*
     * Méthode qui rajoute un opérateur ou un nombre dans l'opération.
     * Dans le TextArea, ça met à jour l'opération mathématique saisie par l'utilisateur avec
     * les bouttons des nombres sur la calculatrice.
     */
    void updateTexte(String charactere) {
        operation = champReponse.getText();
        operation += charactere;
        champReponse.setText(operation);
        champReponse.positionCaret(champReponse.getLength());
    }

    /*
     * Méthode pour ajouter un '-' dans l'opération
     * (Soustraction)
     */
    public void onClickMoins() {
        updateTexte("-");
    }

    /*
     * Méthode pour ajouter un '*' dans l'opération
     * (Multiplication)
     */
    public void onClickFois() {
        if(!verifDernierChar()) updateTexte("*");
    }

    /*
     * Méthode pour ajouter un '/' dans l'opération
     * (Division)
     */
    public void onClickDivise() {
        if(!verifDernierChar()) updateTexte("/");
    }

    /*
     * Méthode pour ajouter un '+' dans l'opération
     * (Addition)
     */
    public void onClickPlus() {
        if(!verifDernierChar()) updateTexte("+");
    }

    /*
     * Méthode pour ajouter un '.' dans l'opération
     * Permet de de calculer avec des nombres a virgule comme 6.5 ou 3.14
     */
    public void onClickPoint() {
        // si il y a rien dans le TextArea, on met 0.nombre
        // et vérifie le dernier char
        if(operation.isEmpty() || verifDernierChar()){
            updateTexte("0.");
        }
        // sinon on ajoute un '.' si il n'en a pas devant (ex: 1.. = impossible)
        else if(!verifDernierChar()) {
            updateTexte(".");
        }
    }

    /*
     * Méthode qui réinitialise le TextArea qui contient l'opération mathématique.
     * Le TextArea devient vide lorsque le boutton C (en rouge) est cliqué.
     */
    public void onClickReset() {
        operation = "";
        champReponse.setText(operation);
        champReponse.positionCaret(champReponse.getLength());
    }

    /*
     * Méthode pour faire le calcul. Cette Méthode utilise le module Math (org.mariuszgromada.math.mxparser.Expression)
     * qui permet de faire des opérations mathématiques à partir d'une string.
     */
    public void onClickEgale()
    {
        // vérification si y'a quelque chose dans le TextArea
        if(champReponse.getText().equals("")) return;

        updateTexte("");

        // Math module
        Expression e = new Expression(operation);
        double v = e.calculate();

        // Compare le résultat si il y a une virgule
        resultat = (v == (int) v) ? Integer.toString((int) v) : Double.toString(v);

        // Écrire le résultat
        champReponse.setText(resultat);
        champReponse.positionCaret(champReponse.getLength()); // met le curseur pour écrire à la fin.
        resultat = "";
    }

    /*
     * Méthode pour checher quel nombre à été appuyer parmis tous les bouttons
     * de nombre.
     */
    public void onClickNombre(ActionEvent event) {
        // récupérer le boutton qui à été cliqué
        Button bouttonClique = (Button) event.getSource(); // casting car getSource() retourne type Object
        String bouttonID = bouttonClique.getId();
        updateTexte(bouttonID.replace("boutton", "")); // prend le fx:id et enleve la string "boutton" pour garder que le nombre
    }

    /*
     * Méthode qui vérifie le dernier caractère de la string operation
     * Évite des erreurs comme 0...1 ou 2+++
     */
    private boolean verifDernierChar(){
        return operation.substring(operation.length() - 1).equals("+") ||
               operation.substring(operation.length() - 1).equals("-") ||
               operation.substring(operation.length() - 1).equals("*") ||
               operation.substring(operation.length() - 1).equals("/") ||
               operation.substring(operation.length() - 1).equals(".");
    }

    @FXML
    public void initialize() {
        // l'usager ne peut pas entrer de chiffre lui même, il doit utiliser les bouttons nombre
        champReponse.setEditable(false);
        champReponse.positionCaret(champReponse.getLength());
    }
}
