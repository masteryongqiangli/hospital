/**
 * 
 */
var img1=new Image();
	img1.src="Res/images/login/schoollogo.jpg";
	var img2=new Image();
	img2.src="Res/images/login/academylogo.jpg";
	 var c;
	var ctx;
	$(function() {
		 c=$('#myCanvas')[0];
		 ctx=c.getContext("2d");
		var x1=	$('#myCanvas').width()*Math.random();
		var y1=	$('#myCanvas').height()*Math.random();
		var x2=	$('#myCanvas').width()*Math.random();
		var y2=	$('#myCanvas').height()*Math.random();
		var x3=	$('#myCanvas').width()*Math.random();
		var y3=	$('#myCanvas').height()*Math.random();
		var x4=	$('#myCanvas').width()*Math.random();
		var y4=	$('#myCanvas').height()*Math.random();
		 
		 fn( x1,y1,x2,y2,x3,y3,x4,y4);
		 
	 
	})
	var xs1=true,ys1=true;
	var xs2=false,ys2=false;
	var xs3=false,ys3=false;
	var xs4=false,ys4=true;
  function fn(x1,y1,x2,y2,x3,y3,x4,y4){ 
	   
		ctx.clearRect(0, 0, $('#myCanvas')[0].width, $('#myCanvas')[0].height);
		
		ctx.drawImage(img1, x1, y1);
		ctx.drawImage(img2, x2, y2);
		ctx.beginPath();
		ctx.arc(x3,y3,10,0,2*Math.PI);
 
		 
		ctx.fillStyle="#ecedee";
		ctx.fill();
		
		ctx.beginPath();
		ctx.arc(x4,y4,8,0,2*Math.PI);
 
		 
		ctx.fillStyle="#ecedee";
		ctx.fill();
		ctx.lineWidth="1";
		ctx.strokeStyle="#ecedee";
		//
		ctx.beginPath();
		ctx.moveTo(x1+img1.width/2,y1+img1.height/2);
		ctx.lineTo(x2+img2.width/2,y2+img2.height/2);
		ctx.stroke();
		//
		ctx.beginPath();
		ctx.moveTo(x1+img1.width/2,y1+img1.height/2);
		ctx.lineTo(x3+2,y3+2);
		ctx.stroke();
		//
		ctx.beginPath();
		ctx.moveTo(x1+img1.width/2,y1+img1.height/2);
		ctx.lineTo(x4+2,y4+2);
		ctx.stroke();
		//
		ctx.beginPath();
		ctx.moveTo(x2+img2.width/2,y2+img2.height/2);
		ctx.lineTo(x3+2,y3+2);
		ctx.stroke();
		//
		ctx.beginPath();
		ctx.moveTo(x2+img2.width/2,y2+img2.height/2);
		ctx.lineTo(x4+2,y4+2);
		ctx.stroke();
		//
		ctx.beginPath();
		ctx.moveTo(x3+2,y3+2);
		ctx.lineTo(x4+2,y4+2);
		ctx.stroke();
		/* ctx.beginPath();
		ctx.moveTo(x1+img1.width/2,y1+img1.height/2);
		ctx.bezierCurveTo(x2,x1,x1,y2,x2+img2.width/2,y2+img2.height/2);
		ctx.stroke();
		ctx.beginPath();
		ctx.moveTo(x1+img1.width/2,y1+img1.height/2);
		ctx.bezierCurveTo(x1,x2,y1,y2,x2+img2.width/2,y2+img2.height/2);
		ctx.stroke(); */
		if(x1+2*img1.width>$('#myCanvas').width()){
			xs1=false
		}
		if(x1<=0){
			xs1=true
		}
		if(xs1){
			x1++;
		}else{
			x1--;
		}
		if(y1+img1.height>$('#myCanvas').height()){
			ys1=false;
		}
		if(y1<=0){
			ys1=true;
		}
		if(ys1){
			y1++;
		}else{
			y1--;
		}
		
		if(x2+3*img2.width>$('#myCanvas').width()){
			xs2=false;
		}
		if(x2<=0){
			xs2=true;
		}
		if(xs2){
			x2++;
		}else{
			x2--;
		}
		if(y2+img2.height>$('#myCanvas').height()){
			ys2=false;
		}
		if(y2<=0){
			ys2=true;
		}
		if(ys2){
			y2++;
		}else{
			y2--;
		}
		
		if(x3+1*50>$('#myCanvas').width()){
			xs3=false;
		}
		if(x3<=0){
			xs3=true;
		}
		if(xs3){
			x3++;
		}else{
			x3--;
		}
		if(y3+50>$('#myCanvas').height()){
			ys3=false;
		}
		if(y3<=0){
			ys3=true;
		}
		if(ys3){
			y3++;
		}else{
			y3--;
		}
		
		
		if(x4+1*50>$('#myCanvas').width()){
			xs4=false;
		}
		if(x4<=0){
			xs4=true;
		}
		if(xs4){
			x4++;
		}else{
			x4--;
		}
		if(y4+50>$('#myCanvas').height()){
			ys4=false;
		}
		if(y4<=0){
			ys4=true;
		}
		if(ys4){
			y4++;
		}else{
			y4--;
		}
		if (true) {
			var t =setTimeout('fn('+x1+','+y1+','+x2+','+y2+','+x3+','+y3+','+x4+','+y4+');', 30);
		}
	}