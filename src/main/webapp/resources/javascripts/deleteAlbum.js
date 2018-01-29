function deleteAlbum(id) {
	if(confirm("Do you want to delete this album?")) 
    {
		/*$.ajax({
            type: "DELETE",
            url: "albumdelete?id="+ id,
            success: function(msg){
                console.log("success");
            	//window.location="manage";
            },
         });*/

      window.location="manage/delete?id="+ id;
    }else{
      return false;
   }
  return true;
}