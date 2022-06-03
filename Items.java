import java.util.Scanner;

public class Items
{
    ItemsData[] itemsdata;
    UserDB userdb;
    int iMaxItems;
    int iLastId;


    Scanner userInput = new Scanner(System.in);

    Items(UserDB userdb, int iMaxItems)
    {
        this.userdb = userdb;
        this.iMaxItems = iMaxItems;

        this.itemsdata = new ItemsData[iMaxItems];

        iLastId = 0;
    }

    void addItem()
    {
        if(!userdb.hasGreaterOrEqualRole(UserData.Roles.ROLE_ADMIN))
        {
            System.out.println("Permission denied.");
            userdb.menuHandler();
            return;
        }

        if(iLastId >= iMaxItems)
        {
            System.out.println("There are too many items already. Sorry :(");
            addItem();
            return;
        }


        System.out.println("Add item name: ");
        String sInputName = userInput.next();
        int iItemId = getId(sInputName);

        if(iItemId != -1)
        {
            System.out.printf("Item already exists");
            addItem();
            return;
        }

        System.out.println("Add item description: ");
        String sInputDescription = userInput.next();
        System.out.println("How many items there are?: ");
        int iInputCount = userInput.nextInt();
        int iInputWhoAdded = userdb.iWhoIsLogged;

        itemsdata[iLastId] = new ItemsData(sInputName, sInputDescription, iInputCount, iInputWhoAdded, false);
        System.out.println("We added your item! Yu-huu!");

        iLastId++;
    }

    void removeItem()
    {
        if(!userdb.hasGreaterOrEqualRole(UserData.Roles.ROLE_ADMIN))
        {
            System.out.println("Permission denied.");
            userdb.menuHandler();
            return;
        }

        System.out.println("What do you want to delete? Type name: ");
        String sName = userInput.nextLine();
        int iItemId = getId(sName);

        if(iItemId == -1)
        {
            System.out.println("Item doesnt exist");
            removeItem();
            return;
        }

        System.out.println("Item removed succesfully!");

        itemsdata[iItemId].bDeleted = true;


    }

    void printItemsList()
    {
        for(int i = 0; i < iLastId; i++)
        {
            if(!itemsdata[i].bDeleted)
            {
                System.out.println("Id " + i + " / name: " + itemsdata[i].sName + " / desc: " + itemsdata[i].sDescription + " / count: " + itemsdata[i].iCount);
            }
        }
    }

    void editItem()
    {
        if(!userdb.hasGreaterOrEqualRole(UserData.Roles.ROLE_PADMIN))
        {
            System.out.println("Permission denied.");
            userdb.menuHandler();
            return;
        }

        System.out.println("What do you want to edit?: ");
        String sName = userInput.next();

        int iItemId = getId(sName);

        if(iItemId == -1)
        {
            System.out.println("Item doesnt exist bro");
            addItem();
            return;
        }

        System.out.println("New item name: ");
        itemsdata[iItemId].sName = userInput.next();

        System.out.println("New item desc: ");
        itemsdata[iItemId].sDescription = userInput.next();

        System.out.println("New item count: ");
        itemsdata[iItemId].iCount = userInput.nextInt();

        System.out.println("Item edited succesfully");
    }

    public int getId(String sName)
    {
        for(int i = 0; i < iLastId; i++)
        {
            if(itemsdata[i].sName.equals(sName))
            {
                return i;
            }
        }
        return -1;
    }

}
