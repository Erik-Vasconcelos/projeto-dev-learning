(function ($) {
    "use strict";

    var name = $('.group-input-contato input[name="nome"]');
    var email = $('.group-input-contato input[name="email"]');
    var assunto = $('.group-input-contato input[name="assunto"]');
    var corpo = $('.group-input-contato textarea[name="corpo"]');

    $("button[name=enviarEmail]").click(function (ev) {
        var check = true;

        if ($(name).val().trim() == '') {
            showValidate(name);
            check = false;
        }

        if ($(assunto).val().trim() == '') {
            showValidate(assunto);
            check = false;
        }

        if ($(email).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
            showValidate(email);
            check = false;
        }

        if ($(corpo).val().trim() == '') {
            showValidate(corpo);
            check = false;
        }

        //Se os campos estiverem válidos, envia o email
        if (check) {
            enviarEmail();
        }

        return check;
    });

    $('.group-input-contato .input-contato').each(function () {
        $(this).focus(function () {
            hideValidate(this);
        });
    });

    function showValidate(input) {
        var thisAlert = $(input);

        $(thisAlert).addClass('is-invalid');
    }

    function hideValidate(input) {
        var thisAlert = $(input);

        $(thisAlert).removeClass('is-invalid');
    }

})(jQuery);

function enviarEmail() {
    let nomeRemetente = $("#nomeRemetente").val();
    let emailRemetente = $("#emailRemetente").val();
    let tituloPostagem = $("#titulo").text();
    let disciplinaPostagem = $("#disciplina").text().replace("<", "").replace("/>", "");
    let assunto = $("#assunto").val();
    let corpo = $("#corpo").val();

    $.ajax({
        method: "POST",
        url: "/post/enviar-email",
        data: JSON.stringify({ nomeRemetente: nomeRemetente, emailRemetente: emailRemetente, tituloPostagem: tituloPostagem, disciplinaPostagem: disciplinaPostagem, assunto: assunto, corpo: corpo }),
        contentType: "application/json; charset=utf-8",
        beforeSend: function () {
            $("#loaderEnvioEmail").css("display", "inline-block");
        },  
        success: function (response) {
            limparCampos();
            $("#loaderEnvioEmail").css("display", "none");
            alert(response);
        }
    }).fail(function (xhr, status, errorThrown) {
        $("#loaderEnvioEmail").css("display", "none");
        alert(xhr.responseText);
    });
}

function limparCampos() {
    $('.group-input-contato input[name="nome"]').val("");
    $('.group-input-contato input[name="email"]').val("");
    $('.group-input-contato input[name="assunto"]').val("");
    $('.group-input-contato textarea[name="corpo"]').val("");
}