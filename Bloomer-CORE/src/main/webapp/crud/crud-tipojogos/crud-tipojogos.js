
$(document).ready(function(){
    listarTipoJogos();
});

function listarTipoJogos(){

 $.getJSON("http://localhost:8080/Bloomer-CORE/tipojogoes.json", function(json){ 

          console.log(json);
          trataJson(json);
          JTable();

    });


// como deve ser
  $.getJSON("usuarios.json", function(json){
       console.log(json);         
    });

}; 

function trataJson (json){
    
}; 

function JTable (json){

	     $('#PersonTableContainer').jtable({
            title: 'TipoJogos',
            actions: {
                listAction: json,
                //createAction: '/GettingStarted/CreatePerson',
               // updateAction: '/GettingStarted/UpdatePerson',
               // deleteAction: '/GettingStarted/DeletePerson'
            },
            fields: {
                id: {
                    key: true,
                    create: false,
                    list: false,
                    edit: false
                },
                nome: {
                    title: 'Nome',
                    width: '30%'
                },
                autor: {
                    title: 'Autor',
                    width: '20%'
                },
                descricao: {
                    title: 'Descrição',
                    width: '15%'
                },
                version: {
                    title: 'Versão',
                    width: '10%'
                },
                niveisDaTaxonomia: {
                    title: 'NivelTaxonomia',
                    width: '20%'
                }
            }
        });

			//Load person list from server
			$('#PeopleTableContainer').jtable('load');
}; 




