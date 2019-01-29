/**
 * Creates an individual container to hold a person's contact information.
 * Contacts will contain the individual's name, home phone number, cell phone
 * number, and an email address.
 * 
 * @author Jordan Date: 11/11/17
 *
 */

public class ContactCreator {

	// variables for a contact name, home phone number, cell phone number
	// and email address
	String contactName, homeNum, cellNum, email;

	/**
	 * Constructor for the ContactCreator class. Takes four parameters and
	 * assigns them to the contact information fields
	 * 
	 * @param newName
	 *            - the value to assign to variable name
	 * @param newHome
	 *            - the value to assign to variable homeNum
	 * @param newCell
	 *            - the value to assign to variable cellNum
	 * @param newEmail
	 *            - the value to assign to variable email
	 */

	public ContactCreator(String newName, String newHome, String newCell, String newEmail) {
		setName(newName);
		setHomePhone(newHome);
		setCellPhone(newCell);
		setEmail(newEmail);
	}

	/**
	 * Default constructor for ContactCreator class. Takes no parameters and
	 * assigns each variable to an empty String.
	 */

	public ContactCreator() {
		clearContact();
	}

	/**
	 * Gets the name of the contact
	 * 
	 * @return contactName
	 */

	public String getName() {
		return contactName;
	}

	/**
	 * Gets the contact's home phone number
	 * 
	 * @return homeNum
	 */

	public String getHome() {
		return homeNum;
	}

	/**
	 * Gets the contact's cell phone number
	 * 
	 * @return cellNum
	 */

	public String getCell() {
		return cellNum;
	}

	/**
	 * Get's the contact's email address
	 * 
	 * @return email
	 */

	public String getEmail() {
		return email;
	}

	/**
	 * Sets a new name for the contact.
	 * 
	 * @param name
	 *            - the new name for the contact
	 */

	public void setName(String name) {
		if (name != null) {
				contactName = name;
		}
	}

	/**
	 * Sets the contact's home phone number to the parameter if it matches 10
	 * characters in length. If not, a prompt alerts the user that the number
	 * was incorrect.
	 * 
	 * @param cell
	 *            - the new cell phone number.
	 */

	public void setHomePhone(String num) {
		StringBuilder temp = new StringBuilder("");

		if (num != null) {
			if (num.length() == 10) {
				temp.append(num.substring(0, 3) + "-" + num.substring(3, 6) + "-" + num.substring(6));
				homeNum = temp.toString();
			}
			else {
				homeNum = num;
			}
		}
	}

	/**
	 * Sets the contact's cell phone number to the parameter if it matches 10
	 * characters in length. If not, a prompt alerts the user that the number
	 * was incorrect.
	 * 
	 * @param num
	 *            - the new cell phone number.
	 */

	public void setCellPhone(String num) {
		StringBuilder temp = new StringBuilder("");

		// if (num.contentEquals("")) {
		// cellNum = num;
		// }
		//

		if (num != null) {
			if (num.length() == 10) {
				temp.append(num.substring(0, 3) + "-" + num.substring(3, 6) + "-" + num.substring(6));
				cellNum = temp.toString();
			}
			else {
				cellNum = num;
			}
		}
		//
		// else {
		// System.out.println("The cell phone number is invalid. It must be 10
		// digits long.");
		// }
	}

	/**
	 * Sets the email variable to the parameter for the contact.
	 * 
	 * @param emailAddress
	 *            - the new email value for the contact
	 */

	public void setEmail(String emailAddress) {
		if (emailAddress != null) {
			email = emailAddress;
		}
	}

	/**
	 * Clears the contact of all information. Takes no parameters and sets the
	 * variables to empty String.
	 */

	public void clearContact() {
		setName("");
		setHomePhone("");
		setCellPhone("");
		setEmail("");
	}

	public String printContact() {
		return this.getName() + "\n" + this.getHome() + "\n" + this.getCell() + "\n" + this.getEmail();
	}
}
