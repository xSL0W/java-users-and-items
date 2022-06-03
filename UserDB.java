import java.util.Scanner;

public class UserDB
{
    int iLastId;
    int iMaxAccounts;
    int iWhoIsLogged;

    UserData[] userdata ;
    Items items;

    Scanner userInput = new Scanner(System.in);

    enum Choice
    {
        CHOICE_VIEW,
        CHOICE_EDIT,
        CHOICE_DELETE,
        CHOICE_ADD_USER,
        CHOICE_ADD_ITEM,
        CHOICE_LOGOUT,
        CHOICE_INVALID
    }

    Choice stringToChoice(String sChoice)
    {
        if      (sChoice.equals("/view"))           return Choice.CHOICE_VIEW;
        else if (sChoice.equals("/edit"))           return Choice.CHOICE_EDIT;
        else if (sChoice.equals("/delete"))         return Choice.CHOICE_DELETE;
        else if (sChoice.equals("/adduser"))        return Choice.CHOICE_ADD_USER;
        else if (sChoice.equals("/additem"))        return Choice.CHOICE_ADD_ITEM;
        else if (sChoice.equals("/logout"))         return Choice.CHOICE_LOGOUT;
        else                                        return Choice.CHOICE_INVALID;
    }

    UserDB(int iMaxAccounts)
    {
        this.iMaxAccounts = iMaxAccounts;

        userdata = new UserData[iMaxAccounts];

        userdata[0] = new UserData("admin", "admin", UserData.Roles.ROLE_ADMIN);
        userdata[1] = new UserData("padmin", "padmin", UserData.Roles.ROLE_PADMIN);
        userdata[2] = new UserData("user", "user", UserData.Roles.ROLE_USER);

        iLastId = 3; // starting the array from 3 because of the pre-defined users above
    }


    /*
    Returns -1 on user not found
    */
    public int getUserId(String sUsername)
    {
        for(int i = 0; i < iLastId; i++)
        {
            if(userdata[i].sUsername.equals(sUsername))
            {
                return i;
            }
        }
        return -1;
    }


    public void register()
    {
        if(!hasGreaterOrEqualRole(UserData.Roles.ROLE_ADMIN))
        {
            System.out.println("Permission denied.");
            menuHandler();
            return;
        }

        if(this.iLastId >= this.iMaxAccounts)
        {
            System.out.println("Register failed! Account limit is: " + this.iMaxAccounts);
            register();
            return;
        }

        System.out.println("Enter username: ");
        String sUsername = userInput.next();

        if(this.getUserId(sUsername) != -1)
        {
            System.out.println("Username already exists, try again");
            register();
            return;
        }

        System.out.println("Insert your super-mega-ultra secret password");
        String sPassword = userInput.next();

        System.out.println("What role this guy should have?");
        System.out.println("- admin");
        System.out.println("- padmin");
        System.out.println("- user");

        UserData.Roles enRole = UserData.stringToRole(userInput.next());

        // Oherwise save data...
        userdata[iLastId] = new UserData(sUsername, sPassword, enRole);

        System.out.println("Succesfully created user");

        this.iLastId++;
    }

    public void login()
    {
        System.out.println("Introdu numele: ");
        String sInputUsername = userInput.next();
        int iUserId = this.getUserId(sInputUsername);

        if(iUserId == -1)
        {
            System.out.println("Sorry, username " + sInputUsername + " was not found in our DB :(");
            this.login(); // retry
            return;
        }

        String sInputPassword = userInput.next();

        if(!sInputPassword.equals(this.userdata[iUserId].sPassword))
        {
            System.out.println("Wrong password, try again");
            this.login(); // retry
            return;
        }

        System.out.println("### You succesfully logged in, yu-huu! ###");

        iWhoIsLogged = iUserId;

        printMenu();
        menuHandler();
    }




    public void printMenu()
    {
        System.out.println("--> Command /view");

        if(userdata[iWhoIsLogged].enRole.ordinal() >= UserData.Roles.ROLE_PADMIN.ordinal())
        {
            System.out.println("--> Command /edit");
        }

        if(userdata[iWhoIsLogged].enRole.ordinal() >= UserData.Roles.ROLE_ADMIN.ordinal())
        {
            System.out.println("--> Command /delete");
            System.out.println("--> Command /adduser");
            System.out.println("--> Command /additem");
            System.out.println("--> Command /logout");
        }
    }

    public void menuHandler()
    {
        Choice userChoice = stringToChoice(userInput.next());

        switch(userChoice)
        {
            case CHOICE_VIEW:
            {
                System.out.println("---> You've choosed /view");
                System.out.println("---> What do you want to view?");
                System.out.println("- users");
                System.out.println("- items");

                String sChoice = userInput.next();

                if(sChoice.equals("users"))
                {
                    printUsersList();
                }
                else if(sChoice.equals("items"))
                {
                    items.printItemsList();
                }

                //printMenu();
                menuHandler();

                break;
            }

            case CHOICE_EDIT:
            {
                System.out.println("---> You've choosed /edit");
                items.editItem();

                //printMenu();
                menuHandler();

                break;
            }

            case CHOICE_DELETE:
            {
                System.out.println("---> You've choosed /delete");
                items.removeItem();

                //printMenu();
                menuHandler();

                break;
            }

            case CHOICE_ADD_ITEM:
            {
                System.out.println("---> You've choosed /additem");
                items.addItem();

                //printMenu();
                menuHandler();

                break;
            }

            case CHOICE_ADD_USER:
            {
                System.out.println("---> You've choosed /adduser");
                register();
                //printMenu();
                menuHandler();

                break;
            }

            case CHOICE_LOGOUT:
            {
                System.out.println("---> You've choosed /logout");
                System.exit(0);

                break;
            }

            default:
            {
                System.out.println("What are you doing sir? Please choose a right command you noob!");
                printMenu();
                menuHandler();

                break;
            }
        }

    }

    void printUsersList()
    {
        System.out.println("##### Users List #####");
        for(int i = 0; i < iLastId; i++)
        {
            System.out.println("[Id " + i + " ] - Username: " + userdata[i].sUsername + " | Permissions Level: " + userdata[i].enRole.ordinal());
        }
        System.out.println("##################");
    }

    public void receiveItems(Items items)
    {
        this.items = items;
    }


    public boolean hasGreaterOrEqualRole(UserData.Roles enRole)
    {
        return (userdata[iWhoIsLogged].enRole.ordinal() >= enRole.ordinal());
    }


}
