function validate(inputClass,validationFieldId){
	var isFormValid=false; 
	var collectionLength=$('.'+inputClass).length;
	
	//input fields validation
	$('.'+inputClass).focusout(function(){
		var controlSum=0;
		
		if($(this).val() === ''){
			$(this).parent().parent().addClass('has-error');
		}
		else{
			$(this).parent().parent().removeClass('has-error');
			
			$('.'+inputClass).each(function(){

				if($(this).val()!=''){
					controlSum+=1;
				}
			});
			
			
			if(collectionLength==controlSum){
				isFormValid=true;
			}
		}
		
		$('#'+validationFieldId).val(isFormValid);
	});
	
 
}

function escapeString(str) {
    return String(str).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}


function SystemUser(userType, navigation){
	var self=this;
	self.type=userType;
	self.navigation=navigation;
}  

function NavigationItem(title,url){
	var self=this;
	self.title=title;
	self.url=url;
}