/**
 * This class represents the Control class which will control the Administrator Users.
 * @author: Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

import java.time.LocalDate;
import java.util.ArrayList;

public class AdminControl implements Communication
{
    private Administrator admin;
    JSS program;

    /**
     * This is the Default constructor for the class.
     */
    public AdminControl() {
        program = new JSS();
        admin = new Administrator();
    }

    /**
     * This is a Non-default constructor for the class.
     * @param user    a User Object representing the Administrator User.
     * @param program the JSS main Control class which spawned this AdminControl
     *                class.
     */
    public AdminControl(Administrator user, JSS program)
    {
        this.admin = user;
        this.program = program;
    // System.out.println("creating admin control okay....");

    }

    /**
     * This method accesses the controlled Admin's User ID.
     * @return
     */
    public int adminID()
    {
        return this.admin.getUserID();
    }

    /**
     * This method sends a Message to a User warning them that their account will
     * be locked.
     * @param senderID   an Integer representing the User ID number of the sending
     *                   Admin.
     * @param receiverID an Integer representing the User ID number of the recipient.
     */
    public void blockedMessage(int senderID, int receiverID)
    {
        boolean sent = false;
        try
        {
            String header = "ACCOUNT LOCK WARNING";
            String text = "Your account will be blocked by Administrator " + this.admin.getUserName();
            text += ". Please contact them immediately to discuss";
            int messageID = this.program.issueMessageID();
            LocalDate date = LocalDate.now();
            Message notification = new Message(messageID, senderID, receiverID,header,text, date);
            program.storeMessage(messageID,false, senderID,receiverID,header,text, -1,date);
            sent = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            sent = false;
        }
    }

    /**
     * This method creates a new Message.
     * @param sender      an Integer representing the User ID of the sender.
     * @param destination an Integer representing the User ID of the recipient.
     * @param header      a String containing the Message subject line.
     * @param body        a String containing the body of the Message.
     */
    public void createMessage(int sender, int destination, String header, String body)
    {
        int messageID = this.program.issueMessageID();
        LocalDate date = LocalDate.now();
        Message message = new Message(messageID,sender,destination,header,body,date);
        this.sendMessage(message);
    }

    /**
     * This is the display method for the class.
     */
    public void display() {
        System.out.println("Program: " + program);
    }
    public String getAdminFirstName()
    {
        return admin.getFirstName();
    }

//    public ArrayList<Message> relayMessages()
//    {
//        ArrayList<Message> toRelay = this.admin.getMessages();
//        return toRelay;
//    }

    /**
     * This is the Accessor method for the admin field.
     * @return the Administrator this Control class controls.
     */
    public Administrator getAdmin() {
        return admin;
    }

    /**
     * This is the Accessor method for the program field.
     * @return the JSS Object which represents the main system.
     */
    public JSS getProgram() {
        return program;
    }

    /**
     * This is the Mutator method for the admin field.
     * @param newAdmin an Administrator to be the Admin for the system.
     */
    public void setAdmin(Administrator newAdmin) {
        admin = newAdmin;
    }

    /**
     * This is the Mutator method for the program field.
     * @param newProgram a JSS Object which represents the main system.
     */
    public void setProgram(JSS newProgram) {
        program = newProgram;
    }

    /**
     * This method toggles a given User between being locked and unlocked.
     * @param userIndex an Integer representing the index in the JSS userList
     *                  field where the desired User can be found.
     */
    public void switchLock(int userIndex)
    {
        try{
            program.alterActive(userIndex);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PromptGUI error = new PromptGUI("Failed to lock", e.toString());
        }
    }

    @Override
    public User relayUser()
    {
        return this.admin;
    }

    @Override
    public JSS relayProgram()
    {
        return program;
    }

    public boolean removeJob(int jobID)
    {
        boolean success = false;
        try
        {
            program.switchJobStatus(jobID);
            success = true;
        }

        catch (Exception e)
        {
            System.out.println("error switching jobs");
            e.printStackTrace();
        }


        return success;
    }
}
