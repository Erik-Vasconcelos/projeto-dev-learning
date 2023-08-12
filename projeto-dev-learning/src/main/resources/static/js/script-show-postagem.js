$(document).ready(function () {
    let a = document.querySelector("#link-voltar")
    let ref = document['referrer'];

    if(ref != '' && typeof ref != 'undefined'){
        if(document.referrer.startsWith(window.location.origin)){
            a.href = ref;
            // history.back();
        }else{
            a.href = window.location.origin;
            // window.location.href = window.location.origin;
        }
    }else{
        a.href = window.location.origin;
        // window.location.href = window.location.origin;
    }
});