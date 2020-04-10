package com.amirs.web.jdbc;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;





/**
 * Servlet implementation class ContactsControllerServlet
 */
@WebServlet("/ContactsControllerServlet")
public class ContactsControllerServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private ContactDbUtil contactDbUtil;
	
	// Define datasource/connection pool for Resource Injection
	@Resource(name="jdbc/contacts_list_management")
	private DataSource datasource;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			
			
			// if the command is missing, then default it to listing student
			if(theCommand == null)
			{
				theCommand = "LIST";
			}

			System.out.println(theCommand);
				
			// route it to the appropriate method
			switch(theCommand)
			{
				case "LIST":
					// list the students ... in MVC fashion
					listContacts(request,response);
					break;
					
				case "ADD":
					addContact(request,response);
					break;
					
				case "LOAD":
					loadContact(request,response);
					break;
					
				case "UPDATE":
					updateContact(request,response);
					break;
					
				case "DELETE":
					deleteContact(request,response);
					break;
					
				case "CSV":
					createCSV(request,response);
					break;
					
				default:
					listContacts(request,response);
			}
			
			
			
		}
		catch(Exception exc)
		{
			throw new ServletException();
		}
	}

	private void createCSV(HttpServletRequest request, HttpServletResponse response) 
	{
		// get students from db util
		List<Contact> contacts = contactDbUtil.getContacts();
		final String CSV_SEPARATOR = ",";
		
		try
		{
			System.out.println("before open file");
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("contacts.csv"), "UTF-8"));
	        System.out.println("after open file");
			for (Contact contact : contacts)
	        {
	            StringBuffer oneLine = new StringBuffer();
	            oneLine.append(contact.getId() <=0 ? "" : contact.getId());
	            oneLine.append(CSV_SEPARATOR);
	            oneLine.append(contact.getFirstName().trim().length() == 0? "" : contact.getFirstName());
	            oneLine.append(CSV_SEPARATOR);
	            oneLine.append(contact.getLastName().trim().length() == 0 ? "" : contact.getLastName());
	            oneLine.append(CSV_SEPARATOR);
	            oneLine.append(contact.getEmail().trim().length( )== 0 ?  "" : contact.getEmail());
	            bw.write(oneLine.toString());
	            bw.newLine();
	        }
	        bw.flush();
	        bw.close();
	        

			//send back to main page (the student list
			listContacts(request,response);
		}
		catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
		catch (Exception e) {}

	}

	private void deleteContact(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		//read student data from form
		int id = Integer.parseInt(request.getParameter("contactId"));
				
		// update the student to DB
		contactDbUtil.deleteContact(id);
								

		//send back to main page (the student list
		listContacts(request,response);
		
	}

	private void updateContact(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		//read student data from form
		int id = Integer.parseInt(request.getParameter("contactId"));
		String first = request.getParameter("firstName");
		String last = request.getParameter("lastName");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
				
		//create new student object
		Contact contact = new Contact(id,first,last,phone,email);
		
		// update the student to DB
		contactDbUtil.updateContact(contact);
						

		//send back to main page (the student list
		listContacts(request,response);
		
	}

	private void loadContact(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		String contactId = request.getParameter("contactId");
		Contact contact = contactDbUtil.getContact(contactId);
		
		request.setAttribute("THE_CONTACT", contact);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-contact-form.jsp");
		dispatcher.forward(request, response);
		
	}

	private void addContact(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//read student data from form
		String first = request.getParameter("firstName");
		String last = request.getParameter("lastName");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
				
		//create new student object
		Contact contact = new Contact(first,last,phone,email);
				
		//add the student to DB
		contactDbUtil.addContact(contact);
				
		//send back to main page (the student list
		listContacts(request,response);
		
	}

	private void listContacts(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// get students from db util
		List<Contact> contacts = contactDbUtil.getContacts();
		
				
		// add students to the request
		request.setAttribute("CONTACTS_LIST", contacts);
				
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-contacts.jsp");
		dispatcher.forward(request, response);
		
	}
	
	@Override
	public void init() throws ServletException 
	{
		super.init();
		
		// create our contact data .. pass it in conn pool / datasource
		try
		{
			contactDbUtil = new ContactDbUtil(datasource);
		}
		catch(Exception exp)
		{
			throw new ServletException(exp);
		}
	}

}
