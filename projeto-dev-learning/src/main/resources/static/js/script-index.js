const topoColumn = document.getElementsByClassName("tecnologias-blog").offsetTop;
const tecnologiasBlog = document.getElementById("tecnologias-blog");

const alturaCta = document.getElementById("tecnologias-blog").clientHeight; //capturar a altura do div fixo
const alturadiv = $("#coluna-tecnologias").height(); //capturar a altura do div fixo
window.onscroll = function () {
    fixar();
}

/*como fazer um div fixed parar em um local*/

function fixar() {
    console.log('Altura div: ' + alturadiv + ' Altura cta: ' + alturaCta);
    console.log('distancia: '  + (alturadiv - alturaCta))
    if (alturadiv - alturaCta > window.pageYOffset) {
        //tecnologiasBlog.classList.add("tecnologias-blog-fixed");
        
        $('#tecnologias-blog').css({
            'position': 'fixed', //fixo a partir deste ponto
            'margin-top': 0 + 'px', //agora novoTop
        });
    } else {
        //tecnologiasBlog.classList.remove("tecnologias-blog-fixed");
         $('#tecnologias-blog').css({
            'position': 'static', //fixo a partir deste ponto
            'margin-top': alturadiv - (alturaCta)+ 'px', //agora novoTop
        });
    }

    /*var alturaBody = $("#coluna-tecnologias").height();
    var alturaDiv = $("#tecnologias-blog").height();
    var offsetTop = $("#tecnologias-blog").offset().top;
    console.log("Altura da secao: "+alturaBody);
    console.log("Altura da div: "+alturaDiv);
    var espacoEntre = alturaBody - (alturaDiv + offsetTop);
    console.log("Dist. entre div e body: "+espacoEntre);

    if (offsetTop > alturaBody - alturaDiv) { //ponto de mudança - 210 pixeis
        let novoTop = '0px'; //começa com 100px que é o normal

        if (espacoEntre <= 0 && espacoEntre > -10) { //ponto de mudança do fim
            novoTop = (alturaBody - alturaDiv) + "px";
            //                  ^----------- altura top que tem normalmente
        }

        $('#tecnologias-blog').css({
            'position': 'static', //fixo a partir deste ponto
            'margin-top': novoTop, //agora novoTop
        });

    } else {
        $('#tecnologias-blog').css({ 'position': 'fixed' }); //se voltou a cima põe estatico
    }


    /* if ($(document).scrollTop() > 300) { //ponto de mudança - 210 pixeis
         let novoTop = '0px'; //começa com 100px que é o normal
         
         if ($(document).scrollTop() > 400){ //ponto de mudança do fim
           novoTop = (alturadiv - alturaCta) + "px";
           //                  ^----------- altura top que tem normalmente
         }
         
         $('#tecnologias-blog').css({
           'position': 'static', //fixo a partir deste ponto
           'margin-top': novoTop, //agora novoTop
         });
     
       } else {
         $('#tecnologias-blog').css({ 'position': 'fixed'  }); //se voltou a cima põe estatico
       }*/





}
