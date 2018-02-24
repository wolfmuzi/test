<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>${fns:getConfig('productName')}</title>
	<meta name="decorator" content="ani"/>
	<script src="${ctxStatic}/plugin/js-menu/contabs.js"></script>
	<script src="${ctxStatic}/common/js/fullscreen.js"></script>
	<link id="theme-tab" href="${ctxStatic}/plugin/js-menu/menuTab-${cookie.theme.value==null?'blue':cookie.theme.value}.css" rel="stylesheet" />
</head>

<body class="">
	<nav class="navbar topnav-navbar navbar-fixed-top" role="navigation">
		<div class="navbar-header text-center">
			<button type="button" class="navbar-toggle" id="showMenu" >
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>

			<a class="navbar-brand J_menuItem"  href="${ctx}/home">${fns:getConfig('projectName')}</a>
		</div>
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav pull-right navbar-right">
				<li class="dropdown color-picker">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
						<span><i class="fa fa-circle"></i></span>
					</a>
					<ul class="dropdown-menu pull-right animated fadeIn" role="menu">
						<li class="padder-h-xs">
							<table class="table color-swatches-table text-center no-m-b">
								<tr>
									<td class="text-center colorr">
										<a href="#" data-theme="blue" class="theme-picker">
											<i class="fa fa-circle blue-base"></i>
										</a>
									</td>
									<td class="text-center colorr">
										<a href="#" data-theme="green" class="theme-picker">
											<i class="fa fa-circle green-base"></i>
										</a>
									</td>
									<td class="text-center colorr">
										<a href="#" data-theme="red" class="theme-picker">
											<i class="fa fa-circle red-base"></i>
										</a>
									</td>
								</tr>
								<tr>
									<td class="text-center colorr">
										<a href="#" data-theme="purple" class="theme-picker">
											<i class="fa fa-circle purple-base"></i>
										</a>
									</td>
									<td class="text-center color">
										<a href="#" data-theme="midnight-blue" class="theme-picker">
											<i class="fa fa-circle midnight-blue-base"></i>
										</a>
									</td>
									<td class="text-center colorr">
										<a href="#" data-theme="lynch" class="theme-picker">
											<i class="fa fa-circle lynch-base"></i>
										</a>
									</td>
								</tr>
							</table>
						</li>
					</ul>
				</li>
				<li>
					<a href="#"  onClick="toggleFullScreen()">
						<span id="fullscreen">全屏 </span>
					</a>

				</li>
			</ul>
		</div>
		<ul class="nav navbar-nav pull-right hidd">	
			<li class="dropdown admin-dropdown" >
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
					<img src="${fns:getUser().photo }" class="topnav-img" alt=""><span class="hidden-sm">${fns:getUser().name}</span> 
				</a>
				<ul class="dropdown-menu animated fadeIn" role="menu">
					<!--
					<li><a class="J_menuItem" href="${ctx}/sys/user/imageEdit">修改头像</a>
                    </li>
                    -->
                    <li><a class="J_menuItem" href="${ctx }/sys/user/info">个人资料</a>
                    </li>
                    <li><a href="${ctx}/logout">安全退出</a>
                    </li>
				</ul>
			</li>	
		</ul>
	</nav>
	<aside id="sidebar">
	<div class="sidenav-outer">
		<div class="side-widgets">
			<div class="text-center" style="padding-top:10px;">

				<a  href="#"><img class="img-circle user-avatar"  src="${ctxStatic}/common/images/iuling_logo1.png" class="user-avatar" /></a>

				<li class="dropdown admin-dropdown" style="padding-top:10px;">
					<a href="#" class="dropdown-toggle"  data-toggle="dropdown" role="button" aria-expanded="false">
						<font color="white">${fns:getUser().name}<b class="caret"></b></font>
					</a>
					<ul class="dropdown-menu animated fade in" style="left:35px" role="menu">
						<!--
						  <li><a class="J_menuItem" href="${ctx}/sys/user/imageEdit">修改头像</a>
                          </li>
                          -->
                          <li><a class="J_menuItem" href="${ctx }/sys/user/info">个人资料</a>
                          </li>
                          <hr>
                          <li><a href="${ctx}/logout">安全退出</a>
                          </li>
					</ul>
				</li>
			</div>
				
			<div class="widgets-content">
				<div class="menu">
					<div class="menu-body">
								<ul class="nav nav-pills nav-stacked sidenav" id="1">
							<t:aniMenu  menu="${fns:getTopMenu()}"></t:aniMenu>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</aside>	
<section id="body-container" class="animation">
             
	
		<!--选项卡  -->
		<div class="main-container" id="main-container">
			<div class="main-content">
				<div class="main-content-inner">
					<div id="breadcrumbs" class="${cookie.tab.value!=false?'breadcrumbs':'un-breadcrumbs'}">
						  <div class="content-tabs">
						    <button id="hideMenu" class="roll-nav roll-left-0 J_tabLeft"><i class="fa fa-align-justify"></i>
						    </button>
							<button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
							</button>
							<nav class="page-tabs J_menuTabs">
								<div class="page-tabs-content">
									 <a href="javascript:;" class="active J_menuTab" data-id="${ctx}/home">首页</a>
								</div>
							</nav>
							<button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
							</button>
							<a href="${ctx}/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
		            	</div>
					</div>

			<div class="J_mainContent"  id="content-main" style="${cookie.tab.value!=false?'height:calc(100% - 40px)':'height:calc(100%)'}">
             <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${ctx}/home" frameborder="0" data-id="${ctx}/home" seamless></iframe>
            </div>
            </div>
            
            
            </div>
            </div>
            
           <div class="footer">
                <%--<div class="pull-left"><a href="http://www.iuling.com">http://www.iuling.com</a> &copy; 2015-2025</div>--%>
            </div>
          
</section>
            
            


<script>

		
$(function(){
	$('#calendar2').fullCalendar({
			eventClick: function(calEvent, jsEvent, view) {
				alert('Event: ' + calEvent.title);
				alert('Coordinates: ' + jsEvent.pageX + ',' + jsEvent.pageY);
				alert('View: ' + view.name);
			}
		});
		$('#rtlswitch').click(function() {
			$('body').toggleClass('rtl');
			var hasClass = $('body').hasClass('rtl');
		});
		$('.theme-picker').click(function() {
			changeTheme($(this).attr('data-theme'));
		});
		$('#showMenu').click(function() {
		    $('body').toggleClass('push-right');
		});
    $('#hideMenu').click(function () {
           $('body').removeClass('push-right')
        $('body').toggleClass('push-left');
    });
		$("#switchTab").change(function(){
			if($("#switchTab").is(':checked')){
				 $("#breadcrumbs").attr("class","breadcrumbs");
				 $("#content-main").css("height","calc(100% - 40px)")
				 $.get('${pageContext.request.contextPath}/tab/true?url='+window.top.location.href,function(result){  });
			}else{
				 $("#breadcrumbs").attr("class","un-breadcrumbs");
				 $("#content-main").css("height","calc(100%)")
				 $.get('${pageContext.request.contextPath}/tab/false?url='+window.top.location.href,function(result){  });
			}
		})
		

});

function changeTheme(theme) {
	var link = $('<link>')
	.appendTo('head')
	.attr({type : 'text/css', rel : 'stylesheet'})
	.attr('href', '${ctxStatic}/common/css/app-'+theme+'.css');

	
	var tabLink = $('<link>')
	.appendTo('head')
	.attr({type : 'text/css', rel : 'stylesheet'})
	.attr('href', '${ctxStatic}/plugin/js-menu/menuTab-'+theme+'.css');
	
	var childrenLink= $('<link>').appendTo($(".J_iframe").contents().find("head"))
	.attr({type : 'text/css', rel : 'stylesheet'})
	.attr('href', '${ctxStatic}/common/css/app-'+theme+'.css');
	
	
	 $.get('${pageContext.request.contextPath}/theme/'+theme+'?url='+window.top.location.href,function(result){
		 
		 $('#theme').remove();
		 $('#theme-tab').remove();
		 $(".J_iframe").contents().find("#theme").remove();
		 link.attr("id","theme");
		 childrenLink.attr("id","theme");
		 tabLink.attr("id","theme-tab")
	 });
		
}
</script>

</body>
</html>