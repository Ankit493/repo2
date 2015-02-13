var express = require('express')
var app = express()

var arr=[];

var bodyparser = require('body-parser')
app.use(bodyparser.json())


app.get('/', function (req, res) {
	console.log(req.body)
 	res.send(arr);
})

/*app.post('/', function(req, res) 
{
		res.writeHead(200,{'Content-Type':'text/plain'});
		arr.push(req.body);
		res.write(JSON.stringify(arr));
		res.send();
});
*/
app.post('/:data', function(req, res) 
{
		arr.push(req.params.data);
		res.writeHead(200,{'Content-Type':'text/plain'});
		res.write(JSON.stringify(arr));
		res.send();
});

/*app.delete('/',function(req,res){

	res.writeHead(200,{'Content-Type':'text/plain'});
	var i=arr.indexOf(req.body);
	if(i>-1)
	{
		arr.splice(i,1);
	}
	res.write(JSON.stringify(arr))
	res.send();
})
*/
app.delete('/:data',function(req,res){
	var i=arr.indexOf(req.params.data);
	if(i>-1)
	{
		arr.splice(i,1);
	}
	res.send(arr);
})

app.put('/:name/:name1',function(req,res){
	var i=arr.indexOf(req.params.name);
	if(i>-1)
	{
		arr.splice(i,1);
	}
	arr.push(req.params.name1);
	req.send(arr);

})

var server = app.listen(3002, function () {

  var host = server.address().address
  var port = server.address().port

  console.log('Example app listening at http://%s:%s', host, port)

})

