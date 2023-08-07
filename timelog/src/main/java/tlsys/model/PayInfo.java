package tlsys.model;

public class PayInfo {
    private int ID;
    private double nbreHeuresBases;
    private double nbreHeuresSuppl;
    private double tauxHoraireBase;
    private double tauxHoraireSuppl;
    private String debut;
    private String fin;

    public PayInfo(int ID, double nbreHeuresBases, double nbreHeuresSuppl, double tauxHoraireBase, double tauxHoraireSuppl, String debut, String fin){
        this.ID = ID;
        this.nbreHeuresBases = nbreHeuresBases;
        this.nbreHeuresSuppl = nbreHeuresSuppl;
        this.tauxHoraireBase = tauxHoraireBase;
        this.tauxHoraireSuppl = tauxHoraireSuppl;
        this.debut = debut;
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "PayInfo [ID=" + ID + ", nbreHeuresBases=" + nbreHeuresBases + ", nbreHeuresSuppl=" + nbreHeuresSuppl
                + ", tauxHoraireBase=" + tauxHoraireBase + ", tauxHoraireSuppl=" + tauxHoraireSuppl + ", debut=" + debut
                + ", fin=" + fin + "]";
    }
    
}
