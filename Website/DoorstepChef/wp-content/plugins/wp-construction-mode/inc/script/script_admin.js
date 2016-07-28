jQuery ( document ).ready( function( $ ) {
    $('.smartcat_construction_font_color_picker').wpColorPicker();
    
    $('.toggle-mode').click( function(){
        
        if( $(this).hasClass('active') ){
            $('#sc-mode').val(0);
        }else{
            $('#sc-mode').val(1);
        }
        
        $(this).toggleClass('active');
    });

    $(".wuc_shortcode").focusout(function () {
        var shortcode = $(this).val();
        shortcode = shortcode.replace(/"/g, "").replace(/'/g, "");
        $(this).val(shortcode);
    });       
    
    $('#wuc_loading').change(function(){
       if($(this).val()=='progress'){
            $('.choose-progress').show();
           $('.choose-time').hide();

       }else if($(this).val() == 'timer'){
            $('.choose-time').show();
           $('.choose-progress').hide();
       }else{
           $('.choose-time').hide();
           $('.choose-progress').hide();
       } 
    });
    
    
    var formfield;
 
    /* user clicks button on custom field, runs below code that opens new window */
    jQuery('.onetarek-upload-button').click(function() {
        formfield = jQuery(this).prev('input'); //The input field that will hold the uploaded file url
        tb_show('','media-upload.php?TB_iframe=true');
 
        return false;
 
    });
    /*
    Please keep these line to use this code snipet in your project
    Developed by oneTarek http://onetarek.com
    */
    //adding my custom function with Thick box close function tb_close() .
    window.old_tb_remove = window.tb_remove;
    window.tb_remove = function() {
        window.old_tb_remove(); // calls the tb_remove() of the Thickbox plugin
        formfield=null;
    };
 
    // user inserts file into post. only run custom if user started process using the above process
    // window.send_to_editor(html) is how wp would normally handle the received data
 
    window.original_send_to_editor = window.send_to_editor;
    window.send_to_editor = function(html){
        if (formfield) {
            fileurl = jQuery('img',html).attr('src');
            jQuery(formfield).val(fileurl);
            tb_remove();
        } else {
            window.original_send_to_editor(html);
        }
    };
        
        
});