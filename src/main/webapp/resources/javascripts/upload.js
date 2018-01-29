function readURL(input) {
    console.log('file size:', input.files[0].size);
  if (input.files && input.files[0]) {
    var reader = new FileReader();
    console.log('file size:', input.files[0].size);
    if (input.files[0].size <= 2 * 1024 * 1024){
        reader.onload = function(e) {
            $('#previewImage').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }

  }
}

$("#uploadImage").change(function() {
  readURL(this);
});