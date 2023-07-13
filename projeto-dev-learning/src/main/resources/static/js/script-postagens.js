
//Função para criar o editor de texto
var editor;
$(document).ready(function () {
    var toolbarOptions = [
        ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
        ['blockquote', 'code-block'],

        [{ 'header': 1 }, { 'header': 2 }],               // custom button values
        [{ 'list': 'ordered' }, { 'list': 'bullet' }],
        [{ 'script': 'sub' }, { 'script': 'super' }],      // superscript/subscript
        [{ 'indent': '-1' }, { 'indent': '+1' }],          // outdent/indent
        [{ 'direction': 'rtl' }],                         // text direction

        [{ 'size': ['small', false, 'large', 'huge'] }],  // custom dropdown
        [{ 'header': [1, 2, 3, 4, 5, 6, false] }],

        [{ 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
        [{ 'font': [] }],
        [{ 'align': [] }],
        
        ['clean']                                         // remove formatting button
    ];

    var options = {
        modules: {
            toolbar: toolbarOptions
        },
        theme: 'snow',
        placeholder: 'Isira o texto aqui...',
    };

    editor = new Quill('#editor', options);

    // *** SET EDITOR CONTENT
    var ic = $("#itemContent").html();
    var delta = editor.clipboard.convert(ic); // convert escaped html
    editor.setContents(delta);
    delta = editor.clipboard.convert(editor.getText(0)); // convert html
    editor.setContents(delta);

    // *** PROCESS FORM SUBMISSION
    $("#submitBtn").on("click", submitForm);

});

//Função para mostrar a imagem quando ela vier do servidor 
$(document).ready(function () {
    // var imageArquive = $("#itemImagem").text();
    var imageArquive = document.getElementById("itemImagem").value;
    var imageContainer = document.getElementById("imagemBanner");

    console.log(imageArquive)
    if (imageArquive != "") {
        imageContainer.src = imageArquive;
    }
});

//Função para renderizar as tecnologias relacionadas com a postagem
$(document).ready(function () {
    let tecnologiaTemp = document.getElementById("tecnologiaTemp").value;
    let tecnologias = JSON.parse("[" + tecnologiaTemp + "]");

    let listaTecnologias = '';
    $.each(tecnologias, function (key, tecnologia) {
        listaTecnologias += '<div class="referencia-tecnologia">';
        listaTecnologias += '<button type="button" onclick="removerTecnologia('
            + JSON.stringify(tecnologia).replaceAll("\"", "\'").replaceAll("\"", "\'") + ')"><img alt="remover" src="/icones/remover.png"></button>';
        listaTecnologias += '<label>' + tecnologia.nome + '</label>';
        listaTecnologias += '</div>';
    });

    document.getElementById("divTecnologias").innerHTML = listaTecnologias;
});

// Funções para salvar o form com o texto inserido no rich text
function submitForm() {
    var delta = editor.getContents();
    var deltaJson = JSON.stringify(delta);
    var corpo = editor.getText();

    images1 = $("#editor img")
    setExplicitDim(images1);
    var justHtml = editor.root.innerHTML; //Pega o html do editor
    // Copy HTML content in hidden form
    $('#inputItemCorpo').val(corpo.replaceAll('\n', ' '));
    $('#inputItemHtml').val(justHtml);

    console.log('valor: '+document.getElementById('imagemBanner').innerText)



    // prepare htmlSnippet
    editor.deleteText(100, editor.getLength())
    var trechoHtml = editor.root.innerHTML;
    images1 = $("#editor img")
    scaleImagesDefault(images1);
    $('#trechoHtml').val(editor.root.innerHTML);

    // Post form
    $("#formPostagem").submit();
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

    if (idTecnologia != 'null') {



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

/*function salvarPostagem() {
    // $('#inputAcao').val('salvarPostagem');
    submitForm();
}*/

/*function mostarImagem() {
    let srcImage = document.getElementById('itemImagem').value;
    let previewImagem = document.getElementById('imagemBanner');

    let contentImage = srcImage;
    console.log('Value: ' + srcImage.value);
    if (contentImage != "") {
        previewImagem.src = contentImage;
    } else {
        previewImagem.src = '/imagens/sem-imagem.jpg';
    }
}*/