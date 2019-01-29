
/**
 * Creates a list of contacts using ContactCreator objects stored in an array.
 * 
 * @author Jordan
 * Date 11/13/17
 *
 */

import java.util.Comparator;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ContactList extends Application {

	private ContactCreator[] contactList;
	private ContactCreator tempContact;
	private Scanner scan;
	private File file;
	private static final int MAX_LENGTH = 30;

	/**
	 * Default constructor for ContactList class. Creates an array of
	 * ContactCreator objects with empty fields. Creates a dummy ContactCreator
	 * object to hold temporary data.
	 * 
	 * @throws IOException
	 */

	public ContactList() {
		setInitialListSize();
		tempContact = new ContactCreator();
		scan = new Scanner(System.in);
	}

	/**
	 * Gets the array of ContactCreator elements.
	 * 
	 * @return - the contactList array
	 */

	public ContactCreator[] getList() {
		return contactList;
	}

	/**
	 * Gets the maximum length a contact list can hold
	 * 
	 * @return - the maximum possible size of the contactList
	 */

	public int getMax() {
		return MAX_LENGTH;
	}

	/**
	 * Sets the initial length of the contact list array to five, and puts empty
	 * contacts in each index
	 */

	public void setInitialListSize() {
		contactList = new ContactCreator[5];
		for (int i = 0; i < contactList.length; i++) {
			contactList[i] = new ContactCreator();
		}
	}

	/**
	 * Compares two different contacts to determine if they are duplicates. If
	 * so, return true. Otherwise return false.
	 * 
	 * @param contact
	 *            - the first contact being compared
	 * @param comp
	 *            - the second contact being compared
	 * @return - true if the contacts are duplicates, else return false.
	 */

	public boolean contactCompare(ContactCreator contact, ContactCreator comp) {
		if (contact.getName().equalsIgnoreCase(comp.getName()) && contact.getHome().equalsIgnoreCase(comp.getHome())
				&& contact.getCell().equalsIgnoreCase(comp.getCell())
				&& contact.getEmail().equalsIgnoreCase(comp.getEmail())) {
			return true;
		}
		return false;
	}

	/**
	 * Compares a contact against those in the list. If a similar contact is
	 * found return true, if not return false.
	 * 
	 * @param check
	 *            the contact to check against the list.
	 * @return - true if the contact exist within the list, else return false.
	 */

	public boolean contactListCompare(ContactCreator contact) {
		for (int i = 0; i < contactList.length; i++) {
			if (contactCompare(contact, contactList[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if a given contact contains empty data fields. If the contact is
	 * blank return true. Else return false.
	 * 
	 * @param check
	 *            -the contact to check
	 * @return - true if the contact's data fields are blank, false if any
	 *         contain information
	 */

	public boolean isBlank(ContactCreator check) {
		if (check.getName().equalsIgnoreCase("") && check.getHome().equalsIgnoreCase("")
				&& check.getCell().equalsIgnoreCase("") && check.getEmail().equalsIgnoreCase("")) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the index in the array of a given contact. If the contact is not
	 * found, -1 is returned instead.
	 * 
	 * @param contact
	 *            - the contact to locate within the array
	 * @return - either the index of the contact, or -1 if it was not found
	 */

	public int getIndex(ContactCreator contact) {
		for (int i = 0; i < contactList.length; i++) {
			if (contactCompare(contact, contactList[i])) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Prints the list of contacts from the array.
	 */

	public void printList() {
		for (int i = 0; i < contactList.length; i++) {
			if (!isBlank(contactList[i])) {
				tempContact = contactList[i];
				System.out.println(tempContact.printContact());
				tempContact.clearContact();
			}
		}
	}

	/**
	 * Prints the contactList from the contact file
	 */

	public void printFileList() {
		FileReader in;
		try {
			in = new FileReader(file);
			BufferedReader buffer = new BufferedReader(in);
			String line = "";

			while (line != null) {
				line = buffer.readLine();
				if (line != null) {
					System.out.println(line);
				}
			}
			buffer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Prints a single contact based off of the name, home/cell number, and
	 * email given. If all parameters match, the contact information will
	 * display.
	 * 
	 * @param name
	 * @param phone
	 * @param cell
	 * @param email
	 */

	public void printContact(String name, String phone, String cell, String email) {
		for (int i = 0; i < contactList.length; i++) {
			if (name.contentEquals(contactList[i].getName()) && phone.contentEquals(contactList[i].getHome())
					&& cell.contentEquals(contactList[i].getCell()) && email.contentEquals(contactList[i].getEmail())) {
				tempContact = contactList[i];
				tempContact.printContact();
				tempContact.clearContact();
			}
		}
	}

	/**
	 * Updates the contact file with new contacts.
	 */

	private void updateFileList(File newFile) {
		FileWriter writer;
		try {
			writer = new FileWriter(newFile);
			for (int i = 0; i < contactList.length; i++) {
				if (!isBlank(contactList[i])) {
					writer.write(contactList[i].printContact());
					if (i != contactList.length - 1) {
						writer.write("\n");
					}
				}
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Sets the file class variable to the given parameter.
	 * 
	 * @param newfile
	 *            - the new value of the file variable
	 */

	public void setFile(File newfile) {
		file = newfile;
	}

	/**
	 * Adds a new contact to the list through the use of data fields.
	 * 
	 * @throws FileNotFoundException
	 */

	public void addFromFile() {
		FileReader in;
		String[][] tempList = new String[30][4];

		int column = 0;
		int row = 0;

		for (int i = 0; i < tempList.length; i++) {
			for (int j = 0; j < tempList[i].length; j++) {
				tempList[i][j] = "";
			}
		}

		try {
			in = new FileReader(file);
			BufferedReader buffer = new BufferedReader(in);
			String line = "";

			while (line != null || row == 31) {
				line = buffer.readLine();
				if (line != null) {
					tempList[row][column] = line;
					++column;
				}
				if (column > 3) {
					column = 0;
					row += 1;
				}
			}
			buffer.close();
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		column = 0;

		for (int i = 0; i < contactList.length; i++) {
			tempContact.clearContact();
			tempContact.setName(tempList[i][column]);
			tempContact.setHomePhone(tempList[i][(column + 1)]);
			tempContact.setCellPhone(tempList[i][column + 2]);
			tempContact.setEmail(tempList[i][column + 3]);

			if (isBlank(contactList[i]) && !contactListCompare(tempContact)) {
				contactList[i].setName(tempList[i][column]);
				contactList[i].setHomePhone(tempList[i][(column + 1)]);
				contactList[i].setCellPhone(tempList[i][column + 2]);
				contactList[i].setEmail(tempList[i][column + 3]);
				sortList();
				if (i == contactList.length - 1) {
					increaseCapacity();
				}
			}
		}
		this.sortList();
	}

	/**
	 * Adds a new contact to the list.
	 * 
	 * @param contact
	 *            -the contact to be added to the list
	 */

	public void addContact(String tempName, String tempHome, String tempCell, String tempEmail) {
		ContactCreator contact = new ContactCreator();
		contact.setName(tempName);
		contact.setHomePhone(tempHome);
		contact.setCellPhone(tempCell);
		contact.setEmail(tempEmail);

		// add contact, increase size method if array full
		for (int i = 0; i < contactList.length; i++) {
			if (isBlank(contactList[i]) && !contactListCompare(contact)) {
				contactList[i].setName(tempName);
				contactList[i].setHomePhone(tempHome);
				contactList[i].setCellPhone(tempCell);
				contactList[i].setEmail(tempEmail);
				sortList();
				if (i == contactList.length - 1) {
					increaseCapacity();
				}
			}
		}
	}

	/**
	 * Edits an entire contact's information
	 * 
	 * @param contact
	 *            - the contact to be edited
	 */

	public void editContact(ContactCreator contact) {

		if (contactListCompare(contact)) {
			System.out.println("Edit name: ");
			contact.setName(scan.nextLine());

			System.out.println("Edit home phone: ");
			contact.setHomePhone(scan.nextLine());

			System.out.println("Edit cell phone: ");
			contact.setCellPhone(scan.nextLine());

			System.out.println("Edit email: ");
			contact.setEmail(scan.nextLine());

			sortList();
		}
	}

	/**
	 * Determines if there are any empty ContactCreator elements in the array.
	 * 
	 * @return - true if there are any empty positions, false otherwise
	 */

	public boolean emptyPos() {
		boolean pos = false;

		for (int i = 0; i < contactList.length; i++) {
			if (this.isBlank(this.contactList[i])) {
				pos = true;
			}
		}
		return pos;
	}

	/**
	 * Sorts the contact list by alphabetical order.
	 */

	public void sortList() {
		for (int i = 0; i < contactList.length - 1; i++) {
			int index = i;
			for (int j = i + 1; j < contactList.length; j++) {
				if (contactList[j].getName().compareToIgnoreCase(contactList[index].getName()) < 0
						&& !isBlank(contactList[j])) {
					index = j;
				}
			}
			ContactCreator smallName = contactList[index];
			contactList[index] = contactList[i];
			contactList[i] = smallName;
		}
	}

	/**
	 * Deletes a contact from the list
	 * 
	 * @param contact
	 *            - the contact to be removed
	 */

	public void deleteContact(ContactCreator contact) {
		if (!isBlank(contact) && contactListCompare(contact) && getIndex(contact) != -1) {
			contactList[getIndex(contact)].clearContact();
			sortList();
			// updateFileList(this.file);
		}
	}

	/**
	 * Increases the size of the contact list by five spaces.
	 */

	private void increaseCapacity() {

		if (contactList.length < MAX_LENGTH) {
			ContactCreator[] tempList = new ContactCreator[contactList.length];

			for (int i = 0; i < contactList.length; i++) {
				tempList[i] = contactList[i];
			}

			contactList = new ContactCreator[contactList.length + 5];
			for (int k = 0; k < contactList.length; k++) {
				contactList[k] = new ContactCreator();
			}
			for (int j = 0; j < tempList.length; j++) {
				contactList[j] = tempList[j];
			}
		}
	}

	/**
	 * Clears the entire list of all contacts.
	 */

	public void clearList() {
		for (int i = 0; i < contactList.length; i++) {
			contactList[i].clearContact();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		ContactList mainList = new ContactList();
		TableView<ContactCreator> table = new TableView<ContactCreator>();
		table.setPlaceholder(new Label("There are no contacts to display"));
		ObservableList<ContactCreator> data = FXCollections.observableArrayList();
		final Button deleteButton = new Button("Delete Contact");
		file = null;

		final Label label = new Label("Address Book");
		label.setFont(new Font("Arial", 30));

		final VBox vbox = new VBox();
		vbox.setMinSize(700, 500);
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(5, 0, 0, 5));

		final HBox hbox = new HBox();

		//////////////////////
		// //
		// MENUBAR CODE //
		// BEGINS //
		// //
		//////////////////////

		MenuBar menuBar = new MenuBar();

		Menu fileMenu = new Menu("File");
		// Menu editMenu = new Menu("Edit");
		Menu helpMenu = new Menu("Help");

		// File Menu items
		MenuItem openFileItem = new MenuItem("Open File");
		MenuItem saveFileItem = new MenuItem("Save List");
		MenuItem exitItem = new MenuItem("Exit");

		// Edit Menu items
		// MenuItem editListItem = new MenuItem("Edit List");

		// Help Menu items
		MenuItem aboutItem = new MenuItem("About");

		fileMenu.getItems().addAll(openFileItem, saveFileItem, exitItem);
		// editMenu.getItems().addAll(editListItem);
		helpMenu.getItems().addAll(aboutItem);
		menuBar.getMenus().addAll(fileMenu, helpMenu);

		/**
		 * Opens a file for a text document containing the contact list
		 */

		openFileItem.setOnAction(e -> {
			mainList.setFile(null);
			FileChooser chooser = new FileChooser();
			file = chooser.showOpenDialog(primaryStage);
			if (file != null) {
				label.setText(file.getName());
				mainList.clearList();
				mainList.setInitialListSize();
				mainList.setFile(file);
				mainList.addFromFile();
				table.getItems().clear();
				data.remove(0, data.lastIndexOf(mainList));
				for (int i = 0; i < mainList.getList().length; i++) {
					if (!mainList.isBlank(mainList.getList()[i])) {
						if ((i > data.size())) {
							data.add(data.size(), mainList.getList()[i]);
						} else {
							data.add(i, mainList.getList()[i]);
						}
						// data.add(i, mainList.getList()[i]);
					}
				}
				Comparator<ContactCreator> compare = Comparator.comparing(ContactCreator::getName);
				FXCollections.sort(data, compare);
			}
		});

		/**
		 * Saves the current contact list to a file.
		 */

		saveFileItem.setOnAction(e -> {
			FileChooser chooser = new FileChooser();
			File tempFile = chooser.showSaveDialog(primaryStage);
			mainList.updateFileList(tempFile);
		});

		/**
		 * Exits the program.
		 */

		exitItem.setOnAction(actionEvent -> Platform.exit());

		/**
		 * Displays information about the program
		 */

		aboutItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Text about = new Text(
						"This is an address book that allows the user to add contacts to their own personal list. "
								+ "\nContacts can be edited and removed from the list at any time. Once you are finished "
								+ "\ncustomizing your book, you can save it for future viewing.");

				VBox aboutBox = new VBox();
				aboutBox.setPadding(new Insets(25, 25, 25, 25));
				aboutBox.getChildren().add(about);
				Scene help = new Scene(aboutBox);
				Stage stage = new Stage();
				stage.setTitle("About Address Book");
				stage.setScene(help);
				stage.setX(primaryStage.getX() + 100);
				stage.setY(primaryStage.getY() + 100);
				stage.show();
			}
		});

		//////////////////////
		// //
		// MENUBAR CODE //
		// ENDS //
		// //
		//////////////////////

		table.setEditable(true);
		// SortedtList(data);

		TableColumn<ContactCreator, String> nameCol = new TableColumn<ContactCreator, String>("Name");
		nameCol.setMinWidth(192);
		nameCol.setMaxWidth(192);
		nameCol.setCellValueFactory(new PropertyValueFactory<ContactCreator, String>("Name"));

		nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		nameCol.setOnEditCommit(new EventHandler<CellEditEvent<ContactCreator, String>>() {
			@Override
			public void handle(CellEditEvent<ContactCreator, String> t) {
				((ContactCreator) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setName(t.getNewValue());
				Comparator<ContactCreator> compare = Comparator.comparing(ContactCreator::getName);
				FXCollections.sort(data, compare);
				for (int i = 0; i < data.size(); i++) {
					if (mainList.isBlank(data.get(i))) {
						// mainList.getList()[mainList.getIndex((data.get(i)))].clearContact();
						// mainList.sortList();
						// mainList.printList();
						data.remove(i);
					}
				}
				table.setVisible(false);
				table.setVisible(true);
			}
		});

		table.getSortOrder().add(nameCol);

		TableColumn<ContactCreator, String> homeCol = new TableColumn<ContactCreator, String>("Home Phone");
		homeCol.setMinWidth(192);
		homeCol.setMaxWidth(192);
		homeCol.setCellValueFactory(new PropertyValueFactory<ContactCreator, String>("Home"));

		homeCol.setCellFactory(TextFieldTableCell.forTableColumn());
		homeCol.setOnEditCommit(new EventHandler<CellEditEvent<ContactCreator, String>>() {
			@Override
			public void handle(CellEditEvent<ContactCreator, String> t) {
				((ContactCreator) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setHomePhone(t.getNewValue());
				table.refresh();
			}
		});

		TableColumn<ContactCreator, String> cellCol = new TableColumn<ContactCreator, String>("Cell Phone");
		cellCol.setMinWidth(192);
		cellCol.setMaxWidth(192);
		cellCol.setCellValueFactory(new PropertyValueFactory<ContactCreator, String>("Cell"));

		cellCol.setCellFactory(TextFieldTableCell.forTableColumn());
		cellCol.setOnEditCommit(new EventHandler<CellEditEvent<ContactCreator, String>>() {
			@Override
			public void handle(CellEditEvent<ContactCreator, String> t) {
				((ContactCreator) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setCellPhone(t.getNewValue());
				table.refresh();
			}
		});

		TableColumn<ContactCreator, String> emailCol = new TableColumn<ContactCreator, String>("Email Address");
		emailCol.setMinWidth(192);
		emailCol.setMaxWidth(192);
		emailCol.setCellValueFactory(new PropertyValueFactory<ContactCreator, String>("Email"));

		emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
		emailCol.setOnEditCommit(new EventHandler<CellEditEvent<ContactCreator, String>>() {
			@Override
			public void handle(CellEditEvent<ContactCreator, String> t) {
				((ContactCreator) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setEmail(t.getNewValue());
				table.refresh();
			}
		});

		table.setItems(data);
		table.getColumns().addAll(nameCol, homeCol, cellCol, emailCol);

		final TextField addName = new TextField();
		addName.setPromptText("FirstName LastName: Limit 40");
		addName.setMinWidth(nameCol.getMaxWidth() - 15);
		addName.setMaxWidth(nameCol.getMaxWidth() - 15);

		final TextField addHome = new TextField();
		addHome.setPromptText("10 digits: No spaces");
		addHome.setMinWidth(homeCol.getMaxWidth() - 15);
		addHome.setMaxWidth(homeCol.getMaxWidth() - 15);

		final TextField addCell = new TextField();
		addCell.setPromptText("10 digits: No spaces");
		addCell.setMinWidth(cellCol.getMaxWidth() - 15);
		addCell.setMaxWidth(cellCol.getMaxWidth() - 15);

		final TextField addEmail = new TextField();
		addEmail.setPromptText("name@mailclient.com");
		addEmail.setMinWidth(emailCol.getMaxWidth() - 40);
		addEmail.setMaxWidth(emailCol.getMaxWidth() - 40);

		final Button addButton = new Button("Add Contact");

		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// TODO Auto-generated method stub
				if (mainList.emptyPos()) {
					mainList.addContact(addName.getText(), addHome.getText(), addCell.getText(), addEmail.getText());
					table.getItems().clear();
					data.clear();
					for (int i = 0; i < mainList.getList().length; i++) {
						if (!mainList.isBlank(mainList.getList()[i])) {
							if ((i > data.size())) {
								data.add(data.size(), mainList.getList()[i]);
							} else {
								data.add(i, mainList.getList()[i]);
							}
						}
					}
				} else {
					Text alert = new Text("The address book is full! \n\nPlease delete contacts "
							+ "before attempting to add a new one.");
					VBox listFull = new VBox();
					listFull.setPadding(new Insets(15, 15, 15, 15));
					listFull.getChildren().add(alert);

					Stage stage = new Stage();
					stage.setScene(new Scene(listFull));
					stage.setX(primaryStage.getX() + 100);
					stage.setY(primaryStage.getY() + 100);
					stage.show();
				}
				addName.clear();
				addHome.clear();
				addCell.clear();
				addEmail.clear();
			}
		});

		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				ObservableList<ContactCreator> delete = table.getSelectionModel().getSelectedItems();

				for (int i = 0; i < delete.size(); i++) {
					System.out.println(delete.get(i).getName());
					mainList.getList()[mainList.getIndex((delete.get(i)))].clearContact();
					delete.forEach(data::remove);
					mainList.sortList();
				}
			}
		});

		hbox.getChildren().addAll(addName, addHome, addCell, addEmail, addButton);
		// hbox.getChildren().get(4).setTranslateX(0);
		// hbox.getChildren().get(5).setTranslateY(30);
		hbox.setSpacing(3);
		vbox.getChildren().addAll(menuBar, label, table);
		vbox.getChildren().add(hbox);
		vbox.getChildren().add(deleteButton);

		Scene myScene = new Scene(new Group());
		primaryStage.setTitle("Address Book");
		primaryStage.setMinWidth(800);
		primaryStage.setMaxWidth(800);
		primaryStage.setMinHeight(580);
		primaryStage.setMaxHeight(580);

		((Group) myScene.getRoot()).getChildren().addAll(vbox);

		primaryStage.setScene(myScene);
		primaryStage.show();
	}
}
