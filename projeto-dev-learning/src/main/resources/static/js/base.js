let file = document.getElementById("inputImagemBanner");
let imagem = document.getElementById("imagemBanner");

file.addEventListener('change', () => {
	if(file.files.length <= 0){
		return;
	}
	
	var size = file.files[0].size;
    if(size > 4194304) {
      alert('O tamanho do arquivo não pode ser maior do que 4Mb')
      file.value = "";   
      
      return;      
    }
	
	let reader = new FileReader();
	
	reader.onload = () => {
		imagem.src = reader.result;
	}
	
	reader.readAsDataURL(file.files[0]);
});
