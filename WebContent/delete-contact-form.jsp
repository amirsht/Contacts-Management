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
						<c:url var="tempLink" value="ContactsControllerServlet">
						<c:param name="command" value="LOAD"/>
						<c:param name="contactId" value="${temp.getId()}"/>
					</c:url>
	<div class="container">
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-5">
						<h2>Edit <b>Contact # ${THE_CONTACT.id}</b></h2>
					</div>
					<div class="col-sm-7">
						<a href="#" class="btn btn-primary"><i class="material-icons">&#xE147;</i> <span>Add New User</span></a>
						<a href="#" class="btn btn-primary"><i class="material-icons">&#xE24D;</i> <span>Export to Excel</span></a>						
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
                
                
                <tbody>
                	
                    <tr>
                    	
                    	<td></td>
                    	
						<form action="ContactsControllerServlet" method="GET">
						<input type="hidden" name="command" value="UPDATE"/>
						<input type="hidden" name="contactId" value="${THE_CONTACT.id}"/>
                    	<td><input type="text" name="firstName" value="${THE_CONTACT.firstName}"/></td>
                    	<td><input type="text" name="lastName" value="${THE_CONTACT.lastName}"/></td>
                    	<td><input type="text" name="email" value="${THE_CONTACT.email}"/></td>
                    	<td><input type="text" name="phone" value="${THE_CONTACT.phone}"/></td>
						<td>
						
							<input type="submit" style="display:none;" name="Save" class="save"/>
							<a href="javascript:void(0);" onclick="$('input:submit').click();" class="settings" title="Done" data-toggle="tooltip"><i class="material-icons">done_outline</i></a>
							<a href="ContactsControllerServlet" class="" title="Cancel" data-toggle="tooltip"><i class="material-icons">cancel_presentation</i></a>
							
						</td>
						</form>
                    </tr>
                </tbody>
            </table>
			<div class="clearfix">
                <div class="hint-text">Showing <b>5</b> out of <b>25</b> entries</div>
            </div>
        </div>
    </div>     
	

</body>
</html>