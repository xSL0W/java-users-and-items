public class ItemsData {
    String sName;
    String sDescription;
    int iCount;
    int iWhoAdded;
    boolean bDeleted;

    ItemsData(String sName, String sDescription, int iCount, int iWhoAdded, boolean bDeleted)
    {
        this.sName = sName;
        this.sDescription = sDescription;
        this.iCount = iCount;
        this.iWhoAdded = iWhoAdded;
        this.bDeleted = bDeleted;
    }
}
