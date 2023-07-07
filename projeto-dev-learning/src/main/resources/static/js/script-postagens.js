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
    $('#inputAcao').val('addTecnologia');
    window.location.href = "/admin/tecnologias";
    //submitForm();
}

function removerTecnologia(id) {
    $('#inputIdItem').val(id);
    submitForm()
}

function salvarPostagem() {
    $('#inputAcao').val('salvarPostagem');
    submitForm();
}

function mostarImagem() {
    let srcImage = document.getElementById('itemImagem');
    let previewImagem = document.getElementById('imagemBanner');

    if(srcImage.getText() != ''){
        previewImagem.src = srcImage.getText();
    }else{
        previewImagem.src = '/imagens/sem-imagem.jpg';
    }
}