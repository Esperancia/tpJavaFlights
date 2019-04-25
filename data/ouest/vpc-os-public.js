(function( $ ) {
	'use strict';

	$(document).ready(function(){

		var curCompIndex;
		var curComponent;
                var nbrSlidOpt;
                
                    /********************** ajax mode*********************/
                    wp.hooks.addAction('vpc.ajax_loading_complete', vpc_os_load_components);
                     /********************** mode normal*********************/
                    vpc_os_load_components();

                    function vpc_os_load_components(){
                        setTimeout(function(){
                                initCompSlide();
                                initOptSlide();
                            },500);
                    }
                
                    
                    wp.hooks.addAction('vpc.hide_options_or_component',vpc_os_hide_options_or_component_selector);
                    wp.hooks.addAction('vpc.show_options_or_component',vpc_os_show_options_or_component_selector);
               
                function vpc_os_hide_options_or_component_selector(rules_groups){
                   
                   if (rules_groups.result.scope == "component"){
                        $('#'+rules_groups.result.apply_on).parent().parent().hide();
                        $("#vpc-container div[data-cid=" + rules_groups.result.apply_on + "]").find('input:checked').removeAttr('checked').trigger('change');
                   }
                    else if (rules_groups.result.scope == "group_per_component") {
                        var split_apply_value = rules_groups.result.apply_on.split('>');
                        var component = split_apply_value[0];
                        var group = split_apply_value[1];
                        $.each($('div[data-cid=' + component + '].vpc-options .vpc-group .vpc-group-name'), function () {
                            if ($(this).html() == group && $(this).not("[style*='display: none;']").length)
                                $(this).parent().hide();
                        });
                    }
                }
                
                function vpc_os_show_options_or_component_selector(rules_groups){
                    if(rules_groups.result.scope == "component"){
                        $('#'+rules_groups.result.apply_on).parent().parent().show();
                        if ($("#vpc-container div.vpc-options[data-cid=" + rules_groups.result.apply_on + "]").find("input[data-default]").length)
                            $("#vpc-container div.vpc-options[data-cid=" + rules_groups.result.apply_on + "]").find("input[data-default]").click();
                        else if ((!$("#vpc-container div.vpc-options[data-cid=" + rules_groups.result.apply_on + "]").find(".vpc-single-option-wrap").not("[style*='display: none;']").find("input:checked").length) && vpc.select_first_elt == "Yes")
                            $("#vpc-container div.vpc-options[data-cid=" + rules_groups.result.apply_on + "]").find("input").first().click();

                    }
                    else if (rules_groups.result.scope == "group_per_component") {
                        var split_apply_value = rules_groups.result.apply_on.split('>');
                        var component = split_apply_value[0];
                        var group = split_apply_value[1];
                        $.each($('div[data-cid=' + component + '].vpc-options .vpc-group .vpc-group-name'), function () {
                            if ($(this).html() == group) {
                                $(this).parent().show();
                                $(this).parents(".vpc-options").find("input").first().click();
                            }
                        });
                    }
                }

                wp.hooks.addAction('vpc.option_change',vpc_os_option_change );
                function vpc_os_option_change(elt, e){
                    var comp_id= elt.parent().data('cid');
                    var current_comp_id = $('.vpc-active.vpc-current').attr('id');
                  //  console.log($('.vpc-group[data-cid="'+ comp_id + '"]').find(":input:checked").val());
                    if(typeof ($('.vpc-group[data-cid="'+ comp_id + '"]').find(":input:checked").val()) != undefined){
                        var checked_elements_values = $('.vpc-group[data-cid="'+ current_comp_id + '"]').find(":input:checked").map(function () {
                            return $(this).val();
                        }).get().join(' ');
                        $('.vpc-current-component .vpc-choosed-opt').html(checked_elements_values)
                        $('#'+comp_id).find('.vpc-selected').html($('.vpc-group[data-cid="'+ comp_id + '"]').find(":input:checked").val());
                        //$('.vpc-component[data-component_id="'+ comp_id + '"]').find('.vpc-selected').html(checked_elements_values);
                    }
                    
                }
         
		function initCompSlide(){
			$('#vpc-components .vpc-components').not('.slick-initialized').slick({
		    	infinite: false,
		    	slidesToShow: 4,
	  			slidesToScroll: 4,
	  			prevArrow: $('#vpc-components button.vpc-arrow-prev'),
	  			nextArrow: $('#vpc-components button.vpc-arrow-next'),
				responsive: [
				    {
				      breakpoint: 1024,
				      settings: {
				      	infinite: false,
				        slidesToShow: 3,
				        slidesToScroll: 3
				      }
				    },
				    {
				      breakpoint: 600,
				      settings: {
				      	infinite: false,
				        slidesToShow: 2,
				        slidesToScroll: 2
				      }
				    },
				    {
				      breakpoint: 480,
				      settings: {
				      	infinite: false,
				        slidesToShow: 1,
				        slidesToScroll: 1
				      }
				    }
				]
		    });
		}

		function initOptSlide(){
			$('.all_options').not('.slick-initialized').slick({
                                infinite: false,
	  			prevArrow: $('#vpc-options button.vpc-arrow-prev'),
	  			nextArrow: $('#vpc-options button.vpc-arrow-next'),
                slidesToShow: 1,
		    }).on('beforeChange', function(event, slick, currentSlide, nextSlide){
                            slick.slideCount = nbrSlidOpt;
                            //console.log(slick.slideCount);
			});
		}
		

                function load_current_component_options(curComponentId){
                    var curOptions = $('.vpc-single-option-wrap[data-cid="'+ curComponentId + '"]');
                    var curGroups= $('.vpc-group-name[data-cid="'+ curComponentId + '"]');
                    $('.vpc-single-option-wrap').not(curOptions).hide();
                    $('.vpc-group-name').not(curGroups).hide();
                    curOptions.show();
                    curGroups.show();
                }
                
		function adjustOptWidth(elm){
			var visibleWidth = $('.vpc-options').outerWidth(true);
			var curGroupSlide = $('.vpc-group.slick-current');
			var OptCurGroup = curGroupSlide.find('.vpc-single-option-wrap');
			var grNameWidth = curGroupSlide.find('.vpc-group-name').outerWidth(true);
			if ( visibleWidth >= 600 )
				OptCurGroup.outerWidth( (visibleWidth - grNameWidth) / 8 );
			if ( visibleWidth >= 400 && visibleWidth < 600 )
				OptCurGroup.outerWidth( (visibleWidth - grNameWidth) / 5 );
			if ( visibleWidth >= 250 && visibleWidth < 400 )
				OptCurGroup.outerWidth( (visibleWidth - grNameWidth) / 3 );
			if ( visibleWidth < 250 )
				OptCurGroup.outerWidth( (visibleWidth - grNameWidth) / 2 );
		}

                function optionsSlidingBtn(){
                    var totalWidth = 0;
                    var allGroupNameWidth=0;
                    $('.slick-slide .vpc-group-name').each(function() {
                        allGroupNameWidth += $(this).width();
                    });

                    $('.all_options .slick-slide').each(function() {
                        if($(this).css("display")=="block"){
                            $(this).find('.vpc-single-option-wrap').each(function(index) {
                                totalWidth += parseInt($(this).width(), 10) + 30;
                            });
                        }

                        if ( totalWidth <= $('.all_options').outerWidth() ) {
                            $('#vpc-options button.vpc-arrow').addClass('vpc-slick-disabled');
                        } else {
                            nbrSlidOpt = Math.ceil(totalWidth / $('.all_options').outerWidth());
                           // nbrSlidOpt = Math.round((totalWidth / $('.all_options').outerWidth());
                            $('.VPC_Ouando #vpc-options .vpc-options').css({width:totalWidth+allGroupNameWidth});
                            $('#vpc-options button.vpc-arrow').removeClass('vpc-slick-disabled');
                        }
                    });
                }
		
		$(document).on('click', '.vpc-component', function () {
                    curComponent = $(this);
                    $('.vpc-component.vpc-active').removeClass('vpc-active vpc-current');
                    $(this).addClass('vpc-active vpc-current');

                    var componentText = curComponent.find('.vpc-component-name > span:first-child').text()
                            + ': <span class="vpc-choosed-opt">' + 
                            curComponent.find('.vpc-component-name > span:nth-child(2)').text() + '</span>';
                    $('.vpc-current-component .vpc-cname').html(componentText);

                    //make visible title and close button after add text
                    $('.vpc-current-component').css('visibility','visible');

                    $('#vpc-options').removeClass('vpc-hidden').addClass('vpc-shown');
                    $('#vpc-components').removeClass('vpc-shown').addClass('vpc-hidden');

                    curCompIndex = $('#vpc-components .vpc-components').slick('slickCurrentSlide');
                    $('.slick-slider').slick('slickGoTo', 0);//or 'setPosition' to reset

                    adjustOptWidth();		

                    //Filter current component options and show them
                    var curComponentId = curComponent.attr('id');
                    var optBlock = $('.all_options');
                    optBlock.slick('slickFilter', function() {  
                        $('.vpc-options', this).hide();
                        $('.vpc-options', this).parent().parent().hide();
                        $('.vpc-options[data-cid="'+ curComponentId + '"]', this).parent().parent().show(); 
                        $('.vpc-options[data-cid="'+ curComponentId + '"]', this).show();
                        return true; 
                    });

                    optionsSlidingBtn();

                });

            $(document).on('click touchstart', '.vpc-options-close', function (e) {
		e.stopPropagation();
                //$('.vpc-component').removeClass('vpc-active vpc-current').show();
                curComponent.addClass('vpc-current');

                $('.all_options').slick('slickUnfilter');
                $('#vpc-components').removeClass('vpc-hidden').addClass('vpc-shown');
                $('#vpc-options').removeClass('vpc-shown').addClass('vpc-hidden');
                initCompSlide();
                $('.vpc-current-component').css('visibility','hidden');
                $('#vpc-components .vpc-components').slick('slickGoTo', curCompIndex);
            });

            $(document).on("change",'.userfile_upload_form input[type="file"]', function () {
                var comp_id=$(this).parents('.vpc-single-option-wrap').data('cid');
                $('#'+comp_id+' .vpc-selected.txt').html('');
            });
            
            $(document).on("keyup",'[id$="-field"]',function ()
            {
                var comp_id=$(this).parents('.vpc-single-option-wrap').data('cid');
                $('#'+comp_id+' .vpc-selected.txt').html('');
            });
            
            
	});

})( jQuery )