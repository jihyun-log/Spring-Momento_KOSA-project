(($)=>{
  class Monami {
    init(){
      this.header();
      this.section1();
      this.section2();
      this.section3();
      this.section4();
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
    section1(){
      let cnt=0;
      let setId=0;
      let setId2=0;
      let winW=$(window).width();

      $(window).resize(function(){
        winW=$(window).width();
        mainSlide();
        return winW;
      });

      //1. 메인 슬라이드 함수
      function mainSlide(){
        $('#section1 .slide-wrap').stop().animate({left:-winW*cnt},600,function(){
          cnt<0?cnt=2:cnt;
          cnt>2?cnt=0:cnt;
          $('#section1 .slide-wrap').stop().animate({left:-winW*cnt},0);
        })
        pageBtn();
      }

      //2-1. 다음 카운트 함수
      function nextCount(){
        cnt++;
        mainSlide();
      }

      //2-2. 이전 카운트 함수
      function prevCount(){
        cnt--;
        mainSlide();
      }

      //3. 자동 타이머 함수
      function autoTimer(){
        setId=setInterval(nextCount,3000);
      }
      autoTimer();

      //다음 버튼 이벤트
      $('.next-btn').click(function(){
        if($('#section1 .slide-wrap').is(':animated'))
        return;
        pausefn();
        nextCount();
      })
      $('.prev-btn').click(function(){
        pausefn();
        prevCount();
      })

      //5.페이지 버튼 함수 이벤트
      function pageBtn(){
        $('.page-btn').removeClass('on');
        $('.page-btn').eq(cnt>2?0:cnt).addClass('on');
      }

      //6.페이지 버튼 클릭 이벤트
      $('.page-btn').each(function(idx){
        $(this).click(function(){
          pausefn();
          cnt=idx;
          mainSlide();
        })
      })

      //7.정지버튼 이벤트
      $('.pause-btn').click(function(){
        if($(this).children().hasClass('fa-pause')){
          clearInterval(setId);
          $('.pause-btn').children().attr('class','fa fa-play');
        }
        else{
          autoTimer();
          $('.pause-btn').children().attr('class','fa fa-pause');
        }
      })

      //정지함수
      function pausefn(){
        clearInterval(setId);
        clearInterval(setId2);
        $('.pause-btn').children().attr('class','fa fa-play');

        let cnt2=0;
        setId2=setInterval(function(){
          cnt2++;
          if(cnt2>2){
            clearInterval(setId2);
            playfn()
          }
        },1000);
      }

      function playfn(){
        autoTimer();
        $('.pause-btn').children().attr('class','fa fa-pause');
      }
    }
    section2(){
      $('.rotate-btn').on({
        mouseenter:function(){
          $(this).addClass('on');
        },
        focusin:function(){
          $(this).addClass('on');
        },
        mouseleave:function(){
          $(this).removeClass('on');
        },
        focusout:function(){
          $(this).removeClass('on');
        }
      });
    }
    section3(){
      let cnt2=0;
      let cnt3=0;
      let cnt4=0;

      let conSlideW = $('#section3 .slide-container .slide').width();

      resizeCon();

      function resizeCon(){
        conSlideW = $('#section3 .slide-container .slide').width();
        mainSlide1();
        mainSlide2();
        mainSlide3();
      }

      $(window).resize(function(){
        resizeCon();
      });

      function mainSlide1(){
        $('#section3 .slide-wrap.wrap1').stop().animate({left:-conSlideW*cnt2},600,function(){
          cnt2>17?cnt2=0:cnt2;
          cnt2<0?cnt2=17:cnt2;
          $('#section3 .slide-wrap.wrap1').stop().animate({left:-conSlideW*cnt2},0)
        })
      }

      function mainSlide2(){
        $('#section3 .slide-wrap.wrap2').stop().animate({left:-conSlideW*cnt3},600,function(){
          cnt3>19?cnt3=0:cnt3;
          cnt3<0?cnt3=19:cnt3;
          $('#section3 .slide-wrap.wrap2').stop().animate({left:-conSlideW*cnt3},0)
        })
      }

      function mainSlide3(){
        $('#section3 .slide-wrap.wrap4').stop().animate({left:-conSlideW*cnt4},600,function(){
          cnt4>10?cnt4=0:cnt4;
          cnt4<0?cnt4=10:cnt4;
          $('#section3 .slide-wrap.wrap4').stop().animate({left:-conSlideW*cnt4},0)
        })
      }

      function nextCount1(){
        cnt2++;
        mainSlide1();
      }

      function nextCount2(){
        cnt3++;
        mainSlide2();
      }

      function nextCount3(){
        cnt4++;
        mainSlide3();
      }

      function prevCount1(){
        cnt2--;
        mainSlide1();
      }

      function prevCount2(){
        cnt3--;
        mainSlide2();
      }

      function prevCount3(){
        cnt4--;
        mainSlide3();
      }

      $('.sec3-prev-btn1').on({
        click:function(e){
          e.preventDefault();
          if(!$('#section3 .slide-wrap.wrap1').is(':animated')){
            prevCount1();
            return;
          }
        }
      });

      $('.sec3-prev-btn2').on({
        click:function(e){
          e.preventDefault();
          if(!$('#section3 .slide-wrap.wrap2').is(':animated')){
            prevCount2();
            return;
          }
        }
      });

      $('.sec3-prev-btn4').on({
        click:function(e){
          e.preventDefault();
          if(!$('#section3 .slide-wrap.wrap4').is(':animated')){
            prevCount3();
            return;
          }
        }
      });

      $('.sec3-next-btn1').on({
        click:function(e){
          e.preventDefault();
          if(!$('#section3 .slide-wrap.wrap1').is(':animated')){
            nextCount1();
            return;
          }
        }
      });

      $('.sec3-next-btn2').on({
        click:function(e){
          e.preventDefault();
          if(!$('#section3 .slide-wrap.wrap2').is(':animated')){
            nextCount2();
            return;
          }
        }
      });

      $('.sec3-next-btn4').on({
        click:function(e){
          e.preventDefault();
          if(!$('#section3 .slide-wrap.wrap4').is(':animated')){
            nextCount3();
            return;
          }
        }
      });

      $('.spring-menu').on({
        click:function(e){
          e.preventDefault();
          $(this).addClass('on');
          $('.slide-container.container1').fadeIn(300);
          $('.maker-menu').removeClass('on');
          $('.light-menu').removeClass('on');
          $('.art-menu').removeClass('on');
          $('.slide-container.container2').hide();
          $('.slide-container.container3').hide();
          $('.slide-container.container4').hide();
        }
      });

      $('.maker-menu').on({
        click:function(e){
          e.preventDefault();
          $(this).addClass('on');
          $('.slide-container.container2').fadeIn(300);
          $('.spring-menu').removeClass('on');
          $('.light-menu').removeClass('on');
          $('.art-menu').removeClass('on');
          $('.slide-container.container1').hide();
          $('.slide-container.container3').hide();
          $('.slide-container.container4').hide();
        }
      });

      $('.light-menu').on({
        click:function(e){
          e.preventDefault();
          $(this).addClass('on');
          $('.slide-container.container3').fadeIn(300);
          $('.spring-menu').removeClass('on');
          $('.maker-menu').removeClass('on');
          $('.art-menu').removeClass('on');
          $('.slide-container.container1').hide();
          $('.slide-container.container2').hide();
          $('.slide-container.container4').hide();
        }
      });

      $('.art-menu').on({
        click:function(e){
          e.preventDefault();
          $(this).addClass('on');
          $('.slide-container.container4').fadeIn(300);
          $('.spring-menu').removeClass('on');
          $('.light-menu').removeClass('on');
          $('.maker-menu').removeClass('on');
          $('.slide-container.container1').hide();
          $('.slide-container.container2').hide();
          $('.slide-container.container3').hide();
        }
      });
    }
    section4(){
      window.addEventListener('load', videoScroll);
      window.addEventListener('scroll', videoScroll);

      function videoScroll() {
        if (document.querySelectorAll('video[autoplay]').length > 0) {
          var windowHeight = window.innerHeight,
              videoEl = document.querySelectorAll('video[autoplay]');

          for (var i = 0; i < videoEl.length; i++) {
            var thisVideoEl = videoEl[i],
                videoHeight = thisVideoEl.clientHeight,
                videoClientRect = thisVideoEl.getBoundingClientRect().top;

            if ( videoClientRect <= ( (windowHeight) - (videoHeight*.5) ) && videoClientRect >= ( 0 - ( videoHeight*.5 ) ) ) {
              thisVideoEl.play();
            } else {
              thisVideoEl.pause();
            }
          }
        }
      }

      let winW=$(window).width();
      let winH=$(window).height();
      let vidW=$('.main-video').width();
      let vidH=$('.main-video').height();
      let marT=(winH-vidH/2);
      let marL=(winW-vidW/2);
      let setId2=0;

      $(window).resize(function(){
        clearInterval(setId2);
        resizefn();
      });

      setId2=setInterval(resizefn,100);

      function resizefn(){
        winW=$(window).width();
        winH=$(window).height();
        vidW=$('.main-video').width();
        vidH=$('.main-video').height();
        marT=(winH-vidH)/2;
        marL=(winW-vidW)/2;

        if(winW>vidW){
          $('.main-video').css({width: winW,height: 'auto'});
        }
        if(winH>vidH){
          $('.main-video').css({width: 'auto',height: winH});
        }
        $('.main-video').css({marginTop: marT,marginLeft: marL});
      }
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

