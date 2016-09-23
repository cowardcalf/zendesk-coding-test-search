package test.yangliu.zendesksearch.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import test.yangliu.zendesksearch.models.DynamicAttributesClass;
import test.yangliu.zendesksearch.models.SearchResultMap;
import test.yangliu.zendesksearch.models.SearchValue;

/**
 * The main class of Zendesk Search
 * @author Yang Liu
 *
 */
public class ZendeskSearch {
	
	// Maps of each type of objects
	private Map<String, DynamicAttributesClass> userMap;
	private Map<String, DynamicAttributesClass> organizationMap;
	private Map<String, DynamicAttributesClass> ticketMap;
	// String arrays of each type of objects
	private String[] userFields;
	private String[] organizationFields;
	private String[] ticketFields;

	// Search results map, cache the searched results to
	// increase the speed of repeat searching
	private SearchResultMap resultsMap = new SearchResultMap();

	/**
	 * Constructor
	 * Load files and run the program
	 */
	public ZendeskSearch() {
		init();
		begin();
	}

	/**
	 * Load files and display messages
	 */
	private void init() {
		// load files and update fields
		System.out.println("Starting ...");
		System.out.println("Loading files...");

		System.out.println("Loading users file...");
		userMap = FileIO.readJSONFile("users.json");

		if (userMap.size() > 0)
			userFields = userMap.values().iterator().next().getAttributes();

		System.out.println("Loading organizations file...");
		organizationMap = FileIO.readJSONFile("organizations.json");
		if (organizationMap.size() > 0)
			organizationFields = organizationMap.values().iterator().next()
					.getAttributes();

		System.out.println("Loading tickets file...");
		ticketMap = FileIO.readJSONFile("tickets.json");
		if (ticketMap.size() > 0)
			ticketFields = ticketMap.values().iterator().next()
					.getAttributes();

		System.out.println("Loading finished\n\n");

		System.out.println("Welcome to Zendesk Search System");
	}

	/**
	 * Show main menus and handle the interaction with user
	 */
	private void begin() {
		Scanner input = new Scanner(System.in);
		boolean finished = false;
		while (!finished) { // a loop to allow the program to continue until the
							// user chooses to exit program
			int selection = showMainMenu(input);
			SearchValue searchValue;
			switch (selection) {
			case 1:
			case 2:
			case 3:
				searchValue = showSearchMenu(input);
				search(selection, searchValue);
				break;
			case 4:
				showAvailableFields();
				break;
			case 5:
				finished = true;
				break;
			default:
				System.out.println("\n** Invalid Selection **\n");
			}
		}
		System.out.println("Thanks for using this system.");
	}

	/**
	 * Show main menu
	 * @param input The scanner
	 * @return User's selection number
	 */
	private int showMainMenu(Scanner input) {
		int selection = 0;
		System.out.println("\n******* MENU *******\n");
		System.out.println("1. Search Users");
		System.out.println("2. Search Organizations");
		System.out.println("3. Search tickets");
		System.out.println("4. Available fields");
		System.out.println("5. Exit\n");
		System.out
				.print("Type the number of your selection, and tap the Enter key: ");

		while (!input.hasNextInt() || (selection = input.nextInt()) > 5
				|| selection < 1) {
			input.nextLine();
			System.out
					.print("Wrong selection, please re-type the number of your selection, and tap the Enter key: ");
		}
		input.nextLine(); // flush the input line
		return selection;
	}

	/**
	 * Show search menu and return the inputs wrapped in SearchValue object
	 * @param input the Scanner
	 * @return
	 */
	private SearchValue showSearchMenu(Scanner input) {
		SearchValue sv = new SearchValue();
		String field = null;
		String value = null;
		System.out.print("Please input a search field: ");
		while ((field = input.nextLine().trim()).isEmpty()) {
			System.out.print("Field cannot be empty, please input a field: ");
		}
		System.out.print("Please input a search value: ");
		value = input.nextLine().trim();
		// update SearchValue
		sv.setField(field);
		sv.setValue(value);
		return sv;
	}

	/**
	 * Show available fields of each type of objects 
	 */
	private void showAvailableFields() {
		System.out.println();
		showFields("user", userFields);
		showFields("organization", organizationFields);
		showFields("ticket", ticketFields);
	}
	
	/**
	 * Show available fields by object type and the fields array
	 * @param type type of the object
	 * @param fieldsArray pre-saved fields
	 */
	private void showFields(String type, String[] fieldsArray) {
		System.out.println("------------------------------------");
		System.out.println("Available fields of " + type);
		for (int i = 0; i < fieldsArray.length; i++) {
			System.out.println(fieldsArray[i]);
		}
		System.out.println();
	}

	/**
	 * Search object by user's selection and input
	 * @param selection 1 to 3, maps to user, organization and ticket
	 * @param searchValue the field and value of searching
	 * @return number of object found
	 */
	private int search(int selection, SearchValue searchValue) {
		int found = 0;
		switch (selection) {
		case 1:
			found = searchProcess("user", userFields, userMap, searchValue);
			break;
		case 2:
			found = searchProcess("organization", organizationFields,
					organizationMap, searchValue);
			break;
		case 3:
			found = searchProcess("ticket", ticketFields, ticketMap,
					searchValue);
			break;
		default:
			System.out
					.println("Incorrect selection. Please selcect correct optiont number.");
		}
		return found;
	}

	/**
	 * Search by type of object, the map of the objects and searching field and value
	 * @param type type of object
	 * @param fields available fields of the type, used to check if the searching field is correct
	 * @param map map stored the objects
	 * @param searchValue field and value to search
	 * @return number of item found
	 */
	private int searchProcess(String type, String[] fields,
			Map<String, DynamicAttributesClass> map, SearchValue searchValue) {
		int found = 0;
		System.out.println(); // separate line
		System.out.println("Searching " + type + " for '"
				+ searchValue.getField() + "' with a value of '"
				+ searchValue.getValue() + "'");
		// if field is available
		if (Arrays.asList(fields).contains(searchValue.getField())) {
			String field = searchValue.getField();
			String value = searchValue.getValue();
			// if searching id
			if (field.equals("_id")) {
				DynamicAttributesClass daObj = map.get(value);
				if (daObj != null) {
					System.out.println(daObj.toString());
					found++;
				}

			} else { // search another fields
				// find in results map
				List<DynamicAttributesClass> list = resultsMap.findInResultMap(type, searchValue);
				if(list == null){
					list = new ArrayList<DynamicAttributesClass>();
					for (DynamicAttributesClass obj : map.values()) {
						if (obj.getValue(field).toString().equals(value)) {
							list.add(obj);
						}
					}
					// update result map
					resultsMap.updateResultsMap(type, searchValue, list);
				}
				// Print out results
				for(DynamicAttributesClass dac : list){
					System.out.println(dac.toString());
					System.out.println(); // separate line	
				}
				found = list.size();
			}
		} else {
			System.out.println("\nThe type of objects doesn't contain a field '"
					+ searchValue.getField()
					+ "'.\nPlease check the available fields in main menu.");
		}
		System.out.println("\nFound " + found + " items");
		return found;
	}
}
