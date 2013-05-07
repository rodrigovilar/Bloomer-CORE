
$(document).ready(function(){
    listarTipoJogos();
});

//http://localhost:8080/Bloomer-CORE/tipojogos.json"

function listarTipoJogos(){

 $.getJSON("usuarios.json", function(json){ 

       console.log(json);
     //  alert("Oi");
       JTable();
         
    });
}; 

function JTable (){
	     $('#PersonTableContainer').jtable({
            title: 'TipoJogos',
            actions: {
                listAction: 'usuarios.json',
                //createAction: '/GettingStarted/CreatePerson',
               // updateAction: '/GettingStarted/UpdatePerson',
               // deleteAction: '/GettingStarted/DeletePerson'
            },
             fields: {
                PersonId: {
                    key: true,
                    list: false
                },
                Name: {
                    title: 'Author Name',
                    width: '40%'
                },
                Age: {
                    title: 'Age',
                    width: '20%'
                },
                RecordDate: {
                    title: 'Record date',
                    width: '30%',
                    type: 'date',
                    create: false,
                    edit: false
                }
            }
        });
			//Load person list from server
	$('#PersonTableContainer').jtable('load');
}; 