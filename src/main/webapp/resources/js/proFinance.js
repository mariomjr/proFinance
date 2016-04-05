
function buildMemberRows(members) {
    return _.template( $( "#member-tmpl" ).html(), {"members": members});
}

/* Uses JAX-RS GET to retrieve current member list */
function updateMemberTable() {
    // Display the loader widget
    $.mobile.loading("show");

    $.ajax({
        url: "rest/members",
        cache: false,
        success: function(data) {
            $( "#members" ).empty().append(buildMemberRows(data));
            $( "#member-table" ).table( "refresh" );
        },
        error: function(error) {
            //console.log("error updating table -" + error.status);
        },
        complete: function() {
            // Hide the loader widget
            $.mobile.loading("hide");
        }
    });
}

/*
Attempts to register a new member using a JAX-RS POST.  The callbacks
the refresh the member table, or process JAX-RS response codes to update
the validation errors.
 */
function registerMember(memberData) {
    //clear existing  msgs
    $('span.invalid').remove();
    $('span.success').remove();

    // Display the loader widget
    $.mobile.loading("show");

    $.ajax({
        url: 'rest/members',
        contentType: "application/json",
        dataType: "json",
        type: "POST",
        data: JSON.stringify(memberData),
        success: function(data) {
            //console.log("Member registered");

            //clear input fields
            $('#reg')[0].reset();

            //mark success on the registration form
            $('#formMsgs').append($('<span class="success">Member Registered</span>'));

            updateMemberTable();
        },
        error: function(error) {
            if ((error.status == 409) || (error.status == 400)) {
                //console.log("Validation error registering user!");

                var errorMsg = $.parseJSON(error.responseText);

                $.each(errorMsg, function(index, val) {
                    $('<span class="invalid">' + val + '</span>').insertAfter($('#' + index));
                });
            } else {
                //console.log("error - unknown server issue");
                $('#formMsgs').append($('<span class="invalid">Unknown server error</span>'));
            }
        },
        complete: function() {
            // Hide the loader widget
            $.mobile.loading("hide");
        }
    });
}


function openDialog(){
	PF('load').show();
}
function closeDialog(){
	PF('load').hide();
}
