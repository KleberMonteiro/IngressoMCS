$ = jQuery;
$(document).ready(format());

function format() {
	menuLateral();
	paginaAtual();	
}

function menuLateral() {
	$(function() {
		var paginaAtual = $(".pagina_atual").html();		
		$(".menu_item .img").css("background-position", "0px 0px");		
		$(".menu_item").each(function() {
			var atual = $(this).attr("rel");
			if(atual == paginaAtual){
				$(this).find(".img").css("background-position", "0px 100%");
			}
		});		
	});
	$(function() {
		var paginaAtual = $(".pagina_atual").html();
		$(".menu_item").hover(function() {
			var atual = $(this).attr("rel");
			if(atual != paginaAtual){				
				$(this).find(".img").css("background-position", "0px 100%");
			}
			$(this).css("background-image", "url('/images/fundo_botao_cinza_hover.png')");
		},function(){
			var atual = $(this).attr("rel");
			if(atual != paginaAtual){				
				$(this).find(".img").css("background-position", "0px 0px");
			}
			$(this).css("background-image", "url('/images/fundo_botao_cinza.png')");
		});
	});
}

function showAjaxLoader(id) {
	$("#" + id + " .ajax_loader img").css("display", "block");
}

function hideAjaxLoader(id) {
	$("#" + id + " .ajax_loader img").css("display", "none");
}