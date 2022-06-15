/**
 * This class represents Messages which can be passed back and forth between Users.
 * @author: Team D - Tom Barker, Jakeob Clarke-Kennedy, Bradley Meyn, Hoang Nguyen, Gerard Samson-Dekker
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Message
{
    private int messageID;
    private int senderID;
    private int receiverID;
    private boolean hasReceived;
    private String header;
    private String body;
    private LocalDate sentDate;

    /**
     * This is the Default constructor for the class.
     */
    public Message()
    {
        this.messageID = 0;
        this.senderID = -1;
        this.receiverID = -1;
        this.hasReceived = false;
        this.header = "";
        this.body = "";
        this.sentDate = LocalDate.now();

    }


    /**
     * This is a Non-default constructor for the class.
     *
     * @param senderID   an Integer representing the ID number of the sender.
     * @param receiverID an Integer representing the ID number of the recipient.
     * @param header     a String containing the subject line of the Message.
     * @param body       a String containing the body of the Message.
     * @param sentDate   a LocalDate containing the date the message was sent
     */
    public Message(int senderID, int receiverID, String header, String body, LocalDate sentDate)
    {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.hasReceived = false;
        this.header = header;
        this.body = body;
        this.sentDate = sentDate;
    }

    /**
     * This is a Non-default constructor for the class.
     *
     * @param nextMessageID an Integer representing the ID number of this Message.
     * @param status        a String describing the current Status of this Message.
     * @param sender        an Integer representing the ID number of the sender.
     * @param messageTo     an Integer representing the ID number of the recipient.
     * @param header        a String containing the subject line of the Message.
     * @param body          a String containing the body of the Message.
     * @param sentDate      a LocalDate containing the date the message was sent
     */
    public Message(int nextMessageID, String status, int sender, int messageTo, String header, String body, LocalDate sentDate)
    {
        this.messageID = nextMessageID;
        this.senderID = sender;
        this.receiverID = messageTo;

        this.header = header;
        this.body = body;

        this.hasReceived = status.equalsIgnoreCase("delivered");
        this.sentDate = sentDate;
    }

    /**
     * This is a Non-default constructor for this class.
     *
     * @param messageID  an Integer representing the ID number of this Message.
     * @param senderID   an Integer representing the ID number of the sender.
     * @param receiverID an Integer representing the ID number of the recipient.
     * @param header     a String containing the subject line of the Message.
     * @param text       a String containing the body of the Message.
     * @param sentDate   a LocalDate containing the date the message was sent
     */
    public Message(int messageID, int senderID, int receiverID, String header, String text, LocalDate sentDate)
    {
        this.messageID = messageID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.header = header;
        this.body = text;
        this.sentDate = sentDate;
    }

    /**
     * This is the display method for the class.
     */
    public void display()
    {
        System.out.println("Message ID:" + messageID);
        System.out.println("Sender ID:" + senderID);
        System.out.println("Recipient ID: " + receiverID);
        System.out.println("Has been received?" + hasReceived);
        System.out.println("Header: " + header);
        System.out.println("Body:" + body);
        System.out.println("Sent Date: " + sentDate);
    }

    /**
     * This is the Accessor method for the body field.
     *
     * @return a String representing the body of the Message.
     */
    public String getBody()
    {
        return body;
    }

    /**
     * This is the Accessor method for the header field.
     *
     * @return a String representing the header of the Message.
     */
    public String getHeader()
    {
        return header;
    }

    /**
     * This is the Accessor method for the messageID field.
     *
     * @return an Integer representing the ID number of this Message.
     */
    public int getMessageID()
    {
        return messageID;
    }

    /**
     * This is the Accessor method for the receiverID field.
     *
     * @return an Integer representing the User ID number of the recipient of
     * this Message.
     */
    public int getReceiverID()
    {
        return receiverID;
    }

    /**
     * This is the Accessor method for the senderID field.
     *
     * @return an Integer representing the User ID number of the sender of
     * this Message.
     */
    public int getSenderID()
    {
        return senderID;
    }

    /**
     * This is the Accessor method for the sentDate field.
     *
     * @return an LocalDate representing the date the message was sent
     */
    public LocalDate getSentDate()
    {
        return sentDate;
    }


    /**
     * This is the Accessor method for the hasReceived field.
     *
     * @return a Boolean representing if this Message has received by the
     * recipient yet, or not.
     */


    public boolean isHasReceived()
    {
        return hasReceived;
    }

    /**
     * This is the Mutator method for the body field.
     *
     * @param body a String representing the new body of the Message.
     */
    public void setBody(String body)
    {
        this.body = body;
    }

    /**
     * This is the Mutator method for the hasReceived field.
     *
     * @param hasReceived a Boolean representing if the Message has been received
     *                    by the recipient or not.
     */
    public void setHasReceived(boolean hasReceived)
    {
        this.hasReceived = hasReceived;
    }

    /**
     * This is the Mutator method for the header field.
     *
     * @param header a String representing the subject line of the Message.
     */
    public void setHeader(String header)
    {
        this.header = header;
    }

    /**
     * This is the Mutator method for the messageID field.
     *
     * @param messageID an Integer which represents the ID number for this Message.
     */
    public void setMessageID(int messageID)
    {
        this.messageID = messageID;
    }

    /**
     * This is the Mutator method for the receiverID field.
     *
     * @param receiverID an Integer which represents the ID number of the recipient
     *                   of this Message.
     */
    public void setReceiverID(int receiverID)
    {
        this.receiverID = receiverID;
    }

    /**
     * This is the Mutator method for the senderID field.
     *
     * @param senderID an Integer which represents the ID number of the sender
     *                 of this Message.
     */


    public void setSenderID(int senderID)
    {
        this.senderID = senderID;
    }

    /**
     * This is the Mutator method for the sentDate field.
     *
     * @param sentDate a LocalDate which represents the date the message was sent.
     */
    public void setSentDate(LocalDate sentDate)
    {
        this.sentDate = sentDate;
    }
}