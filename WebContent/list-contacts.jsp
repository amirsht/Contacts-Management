<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
	<title>Contacts App</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Bootstrap User Management Data Table</title>
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round">
	<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		$('[data-toggle="tooltip"]').tooltip();
	});
	</script>
	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>
	
	<div class="container">
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-5">
						<h2>Contacts <b>Management</b></h2>
					</div>
					<div class="col-sm-7">
						<c:url var="exportcsv" value="ContactsControllerServlet">
						<c:param name="command" value="CSV"/>
						</c:url>
						<a href="add-contact-form.jsp" class="btn btn-primary"><i class="material-icons">&#xE147;</i> <span>Add New User</span></a>
						<a href="${exportcsv}" class="btn btn-primary"><i class="material-icons">&#xE24D;</i> <span>Export to Excel</span></a>						
					</div>
                </div>
            </div>
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th></th>
                        <th>First Name</th>						
						<th>Last Name</th>
						<th>Email</th>
                        <th>Phone</th>
						<th>Action</th>
                    </tr>
                </thead>
                
                <c:forEach var="temp" items="${CONTACTS_LIST}">
                <c:url var="editLink" value="ContactsControllerServlet">
						<c:param name="command" value="LOAD"/>
						<c:param name="contactId" value="${temp.getId()}"/>
				</c:url>
				 <c:url var="deleteLink" value="ContactsControllerServlet">
						<c:param name="command" value="DELETE"/>
						<c:param name="contactId" value="${temp.getId()}"/>
				</c:url>
                <tbody>
                	
                    <tr>
                    	<td></td>
                    	<td>${temp.getFirstName()}</td>
						<td>${temp.getLastName()}</td>
						<td>${temp.getEmail()}</td>
						<td>${temp.getPhone()}</td>
                        
						<td>
							<a href="${editLink}" class="settings" title="Edit" data-toggle="tooltip"><i class="material-icons">edit</i></a>
							<a href="${deleteLink}" onclick="if(!(confirm('Are you sure you want to delete contact?'))) return false" class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE5C9;</i></a>
						</td>
                    </tr>
                </tbody>
                </c:forEach>
            </table>
			<div class="clearfix">
                <div class="hint-text">Showing  <b>#</b> entries</div>
            </div>
        </div>
    </div>     
	

</body>
</html>