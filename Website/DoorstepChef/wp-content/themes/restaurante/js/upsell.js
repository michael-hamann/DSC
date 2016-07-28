( function( $ ) {
    if ('undefined' !== typeof prefixL10n) {
        upsell = $('<a class="prefix-upsell-link"></a>')
            .attr('href', prefixL10n.prefixURL)
            .attr('target', '_blank')
            .text(prefixL10n.prefixLabel)
            .css({
                'display' : 'inline-block',
                'background-color' : '#ff3300',
                'color' : '#fff',
                'text-transform' : 'uppercase',
                'padding' : '8px 10px',
                'font-size': '10px',
                'letter-spacing': '1px',
                'line-height': '1.5',
                'clear' : 'both'
            });
        
        upsell_par = $('<p class="upsell_par"></p>')
            .text(prefixL10n.paragraphText)
            .css({
                'display' : 'inline-block',
                'background-color' : '#ffffff',
                'color' : '#ff3300',
                'margin-top' : '6px',
                'font-size': '12px',
                'letter-spacing': '1px',
                'line-height': '1.5',
                'clear' : 'both'
            });  
        
        
        upsell_span = $('<p class="upsell_span"></p>')
            .text(prefixL10n.premiumSpanTxt)
            .css({
                
                'display' : 'block',
                'color' : '#000000',
                'font-size': '10px',
                'letter-spacing': '1px',
                'line-height': '1.5',
                'clear' : 'both'
            });  
            
            
            
        upsell_demo_link = $('<a class="prefix-upsell-demo"></a>')
            .attr('href', prefixL10n.premiumDemoUrl)
            .attr('target', '_blank')
            .text(prefixL10n.premiumDemoTxt)
            .css({
                'display' : 'inline-block',
                'background-color' : '#ff6600',
                'color' : '#fff',
                'text-transform' : 'uppercase',
                'padding' : '8px 10px',
                'font-size': '10px',
                'letter-spacing': '1px',
                'line-height': '1.5',
                'clear' : 'both'
            });
        

        setTimeout(function () {
            $('#accordion-section-themes h3').append(upsell_par).append(upsell).append(upsell_span).append(upsell_demo_link);
        }, 200);

        // Remove accordion click event
        $('.prefix-upsell-link').on('click', function(e) {
            e.stopPropagation();
        });
    }

} )( jQuery );
