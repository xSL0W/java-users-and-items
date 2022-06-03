public class UserData
{
    public Roles enRole;
    public boolean bLogged;
    public String sUsername;
    public String sPassword;

    enum Roles
    {
        ROLE_INVALID,
        ROLE_USER,
        ROLE_PADMIN,
        ROLE_ADMIN,

    }

    static Roles stringToRole(String sInputRole)
    {
        if      (sInputRole.equals("admin"))            return Roles.ROLE_ADMIN;
        else if (sInputRole.equals("padmin"))           return Roles.ROLE_PADMIN;
        else if (sInputRole.equals("user"))             return Roles.ROLE_USER;
        else                                            return Roles.ROLE_INVALID;
    }

    UserData(String sUsername, String sPassword, Roles enRole)
    {
        this.sUsername = sUsername;
        this.sPassword = sPassword;
        this.enRole = enRole;
        //System.out.println("Initializat");
    }


}