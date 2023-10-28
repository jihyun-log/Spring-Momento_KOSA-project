(($)=>{
  class Monami {
    init(){
      this.header();
      this.footer();
      this.goTop();
    }
    header(){
      $('.all').on({
        click:function(e){
          e.preventDefault();
          $(this).toggleClass('on');
          $('.all-menu').stop().slideToggle(600);
          $('.all-sub').show();
        }
      });

      if($(window).width()<=1024){
        $('.sub-title').on({
          click:function(e){
            $('.all-sub').stop().slideToggle(600);
          }
        });
      }

      $('.main-btn').on({
        mouseenter:function(){
          $('.sub').stop().fadeOut(0);
          $(this).next().stop().fadeIn(300);
        },
        focusin:function(){
          $('.sub').stop().fadeOut(0);
          $(this).next().stop().fadeIn(300);
        }
      });

      $('#nav').on({
        mouseleave:function(){
          $('.sub').stop().slideUp(500);
        }
      });

      $('.lang').on({
        click:function(e){
          e.preventDefault();
          $(this).toggleClass('on');
          $('.lang-list').stop().slideToggle(400);
        }
      });
    }

    footer(){
      $('.family-site').on({
        click:function(e){
          e.preventDefault();
          $('.site-list').stop().slideToggle(500);
        }
      });
    }
    goTop(){
      $(window).scroll(function(){
        if($(window).scrollTop()>100){
          $('#goTop').stop().fadeIn(1000);
        }
        else {
          $('#goTop').stop().fadeOut(1000);
        }
      });

      $('.goTop-btn').on({
        click:function(){
          $('html,body').stop().animate({scrollTop:0},500);
        }
      });
    }
  }
  const newMonami = new Monami();
  newMonami.init();
})(jQuery);

   document.getElementById("search-button").addEventListener("click", function(event) {
        event.preventDefault();
        var searchForm = document.getElementById("search-form");
        searchForm.style.display = (searchForm.style.display === "none" || searchForm.style.display === "") ? "block" : "none";
    });
