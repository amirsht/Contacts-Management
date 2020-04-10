package com.amirs.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


public class ContactDbUtil 
{
	
	private DataSource datasource;

	public ContactDbUtil(DataSource datasource) {
		super();
		this.datasource = datasource;
	}

	public List<Contact> getContacts() 
	{
		List<Contact> contacts = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try
		{
			
			// Get a connection
			myConn = datasource.getConnection();
			
			// Create SQL statement
			String sql = "select * from contacts order by first_name";
			myStmt = myConn.createStatement();
			
			// Execute query
			myRs = myStmt.executeQuery(sql);
			
			// Process result set
			while(myRs.next())
			{
				// retrieve data from result set row
				int id = myRs.getInt("contact_id");
				String first = myRs.getString("first_name");
				String last = myRs.getString("last_name");
				String email = myRs.getString("email");
				String phone = myRs.getString("phone");
				
				
				Contact contact = new Contact(id,first,last,phone,email);
				
				// add it to list of students
				contacts.add(contact);
			}
			
			return contacts;
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
			System.out.println("Catch mada");
			return null;
		}
		finally
		{
			// Close JDBC objects
			 close(myConn,myStmt,myRs);
		}
		
	}

	public void addContact(Contact contact) throws Exception 
	{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try
		{
			// Get a connection
			myConn = datasource.getConnection();
			
			// Create SQL statement
			String sql = "insert into contacts "
					 + "(first_name,last_name,email,phone)"
					 + "values (?,?,?,?)";
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, contact.getFirstName());
			myStmt.setString(2, contact.getLastName());
			myStmt.setString(3, contact.getEmail());
			myStmt.setString(4, contact.getPhone());
			
			myStmt.execute();
			
			
		}
		finally
		{
			// Close JDBC objects
			 close(myConn,myStmt,null);
		}
		
	}

	public Contact getContact(String contacttId) throws Exception 
	{
		
		Contact contact = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int id ;
		
		try
		{
			id = Integer.parseInt(contacttId);
			
			// Get a connection
			myConn = datasource.getConnection();
						
			// Create SQL statement
			String sql = "SELECT * from contacts where contact_id=?";
			
			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1, id);
			
			myRs = myStmt.executeQuery();
			
			if(myRs.next())
			{
				String first = myRs.getString("first_name");
				String last = myRs.getString("last_name");
				String email = myRs.getString("email");
				String phone = myRs.getString("phone");

				contact = new Contact(id,first,last,phone,email);
			}
			
			else new Exception("Could not find contact id: "+contacttId);

			return contact;
			
		}
		finally
		{
			// Close JDBC objects
			 close(myConn,myStmt,myRs);
		}
	}

	public void updateContact(Contact contact) throws Exception 
	{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try
		{
			// Get a connection
			myConn = datasource.getConnection();
						
			// Create SQL statement
			String sql = "UPDATE  contacts SET first_name=?, last_name=?, email=? , phone=?"
					+ "where contact_id=?";
			
			myStmt = myConn.prepareStatement(sql);

			myStmt.setString(1, contact.getFirstName());
			myStmt.setString(2, contact.getLastName());
			myStmt.setString(3, contact.getEmail());
			myStmt.setString(4, contact.getPhone());
			myStmt.setInt(5, contact.getId());
			System.out.println(contact);
			
			myStmt.execute();
			
		}
		catch(Exception e)
		{
			System.out.println("catch update");
			e.printStackTrace();
		}
		finally
		{
			// Close JDBC objects
			 close(myConn,myStmt,null);
		}
		
	}
	
	public void deleteContact(int contactId) 
	{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try
		{
			// Get a connection
			myConn = datasource.getConnection();
						
			// Create SQL statement
			String sql = "DELETE from contacts "
					+ "where contact_id=?";
			
			myStmt = myConn.prepareStatement(sql);

			myStmt.setInt(1, contactId);
			myStmt.execute();
			
		}
		catch(Exception e)
		{
			System.out.println("catch update");
			e.printStackTrace();
		}
		finally
		{
			// Close JDBC objects
			 close(myConn,myStmt,null);
		}
	}
	
	private void close(Connection myConn, Statement myStmt, ResultSet myRs) 
	{
		try
		{
			if(myRs != null)
				myRs.close();
			if(myStmt != null)
				myStmt.close();
			if(myConn != null)
				myConn.close();
		}
		catch (Exception exc)
		{
			exc.printStackTrace();
		}
		
	}

	

}
