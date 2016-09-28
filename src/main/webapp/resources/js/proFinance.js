PrimeFaces.locales['pt'] = {  
	closeText: 'Fechar',  
	prevText: 'Anterior',  
	nextText: 'Próximo',  
	currentText: 'Começo',  
	monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],  
	monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun', 'Jul','Ago','Set','Out','Nov','Dez'],  
	dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],  
	dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb'],  
	dayNamesMin: ['D','S','T','Q','Q','S','S'],  
	weekHeader: 'Semana',  
	firstDay: 1,  
	isRTL: false,  
	showMonthAfterYear: false,  
	yearSuffix: '',  
	timeOnlyTitle: 'Só Horas',  
	timeText: 'Tempo',  
	hourText: 'Hora',  
	minuteText: 'Minuto',  
	secondText: 'Segundo',  
	currentText: 'Data Atual',  
	ampm: false,  
	month: 'Mês',  
	week: 'Semana',  
	day: 'Dia',  
	allDayText : 'Todo Dia'  
};

$(document).unbind('keydown').bind('keydown', function (event) {
    var doPrevent = false;
    if (event.keyCode === 8) {
        var d = event.srcElement || event.target;
        if ((d.tagName.toUpperCase() === 'INPUT' && (d.type.toUpperCase() === 'TEXT' || d.type.toUpperCase() === 'PASSWORD' || d.type.toUpperCase() === 'FILE')) 
             || d.tagName.toUpperCase() === 'TEXTAREA') {
            doPrevent = d.readOnly || d.disabled;
        }
        else {
            doPrevent = true;
        }
    }

    if (doPrevent) {
        event.preventDefault();
    }
});

function isNumberFormat(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if(charCode == 44 || charCode == 45){
    	return true;
    }else if(charCode == 13){
    	return false;
    }else if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function openDialog(){
	PF('load').show();
}
function closeDialog(){
	PF('load').hide();
}
