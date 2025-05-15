package application.model;

/**
 * Interface der definerer den metode, som en klasse henter historik på.
 */

public interface Historik {


    /**
     * Henter en StringBuilder, med oplysninger om historik på objektet.
     * @return en StringBuilder med historik
     */
    public StringBuilder hentHistorik();
}
