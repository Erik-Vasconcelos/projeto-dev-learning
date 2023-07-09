// Funções para salvar o form com o texto inserido no rich text
function submitForm() {
    var delta = editor.getContents();
    var deltaJson = JSON.stringify(delta);
    var corpo = editor.getText(); //TExto puro

    images1 = $("#editor img")
    setExplicitDim(images1);
    var justHtml = editor.root.innerHTML; //Pega o html do editor
    // Copy HTML content in hidden form
    $('#inputItemCorpo').val(corpo.replaceAll('\n', ' '));
    $('#inputItemHtml').val(justHtml);

    // prepare htmlSnippet
    editor.deleteText(100, editor.getLength())
    var trechoHtml = editor.root.innerHTML;
    images1 = $("#editor img")
    scaleImagesDefault(images1);
    $('#trechoHtml').val(editor.root.innerHTML);

    // Post form
    $("#formPostagem").submit();
    console.log('Passoi!!!')
}

function scaleImages(images1, minHeight) {
    for (i = 0; i < images1.length; i++) {
        images1[i].removeAttribute("class");
        ratio = images1[i].naturalWidth / images1[i].naturalHeight;
        images1[i].setAttribute('height', minHeight);
        images1[i].setAttribute('width', Math.round(minHeight * ratio));
        console.log(images1[i]);
    }
}
function scaleImagesDefault(images1) {
    scaleImages(images1, 100);
}

function setExplicitDim(images1) {
    for (i = 0; i < images1.length; i++) {
        images1[i].setAttribute('width', images1[i].naturalWidth);
        images1[i].setAttribute('height', images1[i].naturalHeight);
    }
}

// Funcões para manipular as ações de envio do formulário para o servidor
function adicionarTecnologia() {
    var select = document.getElementById("tecnologias");

    let idTecnologia = select.options[select.selectedIndex].value;
    let nomeTecnologia = select.options[select.selectedIndex].text;
    let tecnologiaAdd = '{"id": "' + idTecnologia + '", "nome": "' + nomeTecnologia + '"}';
    let tecnologiaTemp = document.getElementById("tecnologiaTemp").value.replace("[", "").replace("]", "");

    let containsTecnologia = 'false';

    let tecnologias = JSON.parse("[" + tecnologiaTemp + "]");
    $.each(tecnologias, function (key, tecnologiaItem) {
        let tecnologiaJson = JSON.parse(tecnologiaAdd);

        if (JSON.stringify(tecnologiaItem) === JSON.stringify(tecnologiaJson)) {
            containsTecnologia = 'true';
        }
    });

    if (containsTecnologia === 'false') {
        if (tecnologiaTemp == '' || tecnologiaTemp == null) {
            tecnologiaTemp = tecnologiaAdd;
        } else {
            tecnologiaTemp += ", " + tecnologiaAdd;
        }

        tecnologias = JSON.parse("[" + tecnologiaTemp + "]");
        let listaTecnologias = '';
        $.each(tecnologias, function (key, tecnologia) {
            listaTecnologias += '<div class="referencia-tecnologia">';
            listaTecnologias += '<button type="button" onclick="removerTecnologia('
                + JSON.stringify(tecnologia).replaceAll("\"", "\'").replaceAll("\"", "\'") + ')"><img alt="remover" src="/icones/remover.png"></button>';
            listaTecnologias += '<label>' + tecnologia.nome + '</label>';
            listaTecnologias += '</div>';
        });

        document.getElementById("divTecnologias").innerHTML = listaTecnologias;
        document.getElementById("tecnologiaTemp").value = tecnologiaTemp;
    }
}

function removerTecnologia(tecnologiaRemover) {
    tecnologiaRemover = JSON.stringify(tecnologiaRemover);
    let tecnologiaTemp = document.getElementById("tecnologiaTemp").value.replace("[", "").replace("]", "");

    let tecnologias = JSON.parse("[" + tecnologiaTemp + "]");
    $.each(tecnologias, function (key, tecnologiaItem) {

        if (JSON.stringify(tecnologiaItem) === tecnologiaRemover) {

            let tecnologiaTemp = "";
            let listaTecnologias = "";
            //Montando o json novamente sem a tecnologia removida
            $.each(tecnologias, function (key, tecnologia) {

                if (JSON.stringify(tecnologia) != JSON.stringify(tecnologiaItem)) {
                    if (tecnologiaTemp == '' || tecnologiaTemp == null) {
                        tecnologiaTemp = JSON.stringify(tecnologia);
                    } else {
                        tecnologiaTemp += ", " + JSON.stringify(tecnologia);
                    }

                    listaTecnologias += '<div class="referencia-tecnologia">';
                    listaTecnologias += '<button type="button" onclick="removerTecnologia('
                        + JSON.stringify(tecnologia).replaceAll("\"", "\'").replaceAll("\"", "\'") + ')"><img alt="remover" src="/icones/remover.png"></button>';
                    listaTecnologias += '<label>' + tecnologia.nome + '</label>';
                    listaTecnologias += '</div>';
                }
            });

            document.getElementById("divTecnologias").innerHTML = listaTecnologias;
            document.getElementById("tecnologiaTemp").value = tecnologiaTemp;

            //para a execução do each caso a tecnologia tenha sido achada e removida
            return false;
        }
    });
}

function salvarPostagem() {
    // $('#inputAcao').val('salvarPostagem');
    submitForm();
}

function mostarImagem() {
    let srcImage = document.getElementById('itemImagem');
    let previewImagem = document.getElementById('imagemBanner');

    if (srcImage.getText() != '') {
        previewImagem.src = srcImage.getText();
    } else {
        previewImagem.src = '/imagens/sem-imagem.jpg';
    }
}