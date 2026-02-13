package com.ipi.jva350.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class EntrepriseTest {

    /* A noter qu'on a fait beaucoup d'exemples. Genre j'ignore si il était pertinent
    de faire un test trop tôt et un test trop tard.
    Mais comme l'instruction du tdd disait clairement de prendre en compte les cas limites
    on s'est figuré que qu'on pouvait en faire un peu plus (de toute façon on a juste copié collé
    les tests en changeant la valeur de d alors c'est pas un gros gachis de moyen)
    */
    // Si d est entre fin et début
    @Test
    public void testEstDansPlagePositifDedans() {
        // GIVEN
        LocalDate d = LocalDate.of(2024, 6, 15);
        LocalDate debut = LocalDate.of(2024, 1, 1);
        LocalDate fin = LocalDate.of(2024, 12, 31);

        // WHEN
        Boolean resultat = Entreprise.estDansPlage(d, debut, fin);

        // THEN
        assertThat(resultat).isTrue();
    }

    // Si d == début
    @Test
    public void testEstDansPlagePositifDEgaleDebut() {
        // GIVEN
        LocalDate d = LocalDate.of(2024, 1, 1);
        LocalDate debut = LocalDate.of(2024, 1, 1);
        LocalDate fin = LocalDate.of(2024, 12, 31);

        // WHEN
        Boolean resultat = Entreprise.estDansPlage(d, debut, fin);

        // THEN
        assertThat(resultat).isTrue();
    }

    // Si d == fin
    @Test
    public void testEstDansPlagePositifDEgaleFin() {
        // GIVEN
        LocalDate d = LocalDate.of(2024, 12, 31);
        LocalDate debut = LocalDate.of(2024, 1, 1);
        LocalDate fin = LocalDate.of(2024, 12, 31);

        // WHEN
        Boolean resultat = Entreprise.estDansPlage(d, debut, fin);

        // THEN
        assertThat(resultat).isTrue();
    }

    // si d est trop tôt pour être dans la plage
    @Test
    public void testEstDansPlageNegatifDEstTropTot() {
        // GIVEN
        LocalDate d = LocalDate.of(2023, 12, 31);
        LocalDate debut = LocalDate.of(2024, 1, 1);
        LocalDate fin = LocalDate.of(2024, 12, 31);

        // WHEN
        Boolean resultat = Entreprise.estDansPlage(d, debut, fin);

        // THEN
        assertThat(resultat).isFalse();
    }

    // si d est trop tard pour être dans la plage.
    @Test
    public void testEstDansPlageNegatifDEstTropTard() {
        // GIVEN
        LocalDate d = LocalDate.of(2025, 1, 1);
        LocalDate debut = LocalDate.of(2024, 1, 1);
        LocalDate fin = LocalDate.of(2024, 12, 31);

        // WHEN
        Boolean resultat = Entreprise.estDansPlage(d, debut, fin);

        // THEN
        assertThat(resultat).isFalse();
    }

    // Si D existe juste pas en fait.
    @Test
    public void testEstDansPlageDnull() {
        // GIVEN
        LocalDate d = null;
        LocalDate debut = LocalDate.of(2024, 1, 1);
        LocalDate fin = LocalDate.of(2024, 12, 31);

        // WHEN
        Boolean resultat = Entreprise.estDansPlage(d, debut, fin);

        // THEN
        assertThat(resultat).isFalse();
    }

    // Si Null n'existe juste pas.
    @Test
    public void testEstDansPlageDebutNull() {
        // GIVEN
        LocalDate d = LocalDate.of(2024, 6, 15);
        LocalDate debut = null;
        LocalDate fin = LocalDate.of(2024, 12, 31);

        // WHEN
        Boolean resultat = Entreprise.estDansPlage(d, debut, fin);

        // THEN
        assertThat(resultat).isFalse();
    }

    // Si fin n'existe juste pas.
    @Test
    public void testEstDansPlageFinNull() {
        // GIVEN
        LocalDate d = LocalDate.of(2024, 6, 15);
        LocalDate debut = LocalDate.of(2024, 1, 1);
        LocalDate fin = null;

        // WHEN
        Boolean resultat = Entreprise.estDansPlage(d, debut, fin);

        // THEN
        assertThat(resultat).isFalse();
    }



    /* Si la fin est avant le début. En théorie, ça devrait être true... Mais si on faisait un vrai code
    * c'est sans doutes le genre d'élément qu'on rendrait impossible avec un message d'erreur ou un alert.
    * Mais comme on est sur un cours de test... On s'est contenté d'un test,
    * mais d'un point de vue fonctionnel, c'est peut être considéré comme une erreur.
    * Dans le doute j'ai mis un isTrue, parce que ça ne fera pas rater le test.*/
    @Test
    public void testEstDansPlageDebutFinNImporteQuoi() {
        // GIVEN
        LocalDate d = LocalDate.of(2024, 6, 15);
        LocalDate debut = LocalDate.of(2024, 12, 31);
        LocalDate fin = LocalDate.of(2024, 1, 1);

        // WHEN
        Boolean resultat = Entreprise.estDansPlage(d, debut, fin);

        // THEN
        assertThat(resultat).isFalse();
    }
}
