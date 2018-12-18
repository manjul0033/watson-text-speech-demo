'use strict';

//----------ITEM FUNCTION------------------//

		class Item extends React.Component {
  			constructor(props) {
  			  super(props);
			}
			componentDidMount() {
//				this.loadWorkoutDetails();
			}
			componentWillUnmount() {

			}
			render() {
							return (
								<tr>
									<td>{this.props.item.fileName}</td>
									<td>{this.props.item.ExtensionType}</td>
									<td>{this.props.item.DateCreated}</td>
									<td>{this.props.item.DateModified}</td>
									<td>{this.props.item.TimeModified}</td>
									<td>{this.props.item.Language}</td>
								</tr>
							);
			}

		}

//--------------------------------------------------------------//              
		class ItemsTable extends React.Component {
  			constructor(props) {
  			  super(props);
			  this.backToStart = this.backToStart.bind(this);
			}
			componentDidMount() {

			}
			componentWillUnmount() {

			}
			backToStart(props) {
//				alert("fetching members details....");
				ReactDOM.render(
					<StartApp />, document.getElementById('root')
				);
			}
			render() {
							var rows = [];
							this.props.itemsList.forEach(function(item){
								rows.push(<Item item={item} />);
							});

							return (
								<div className="container">
								  <table className="table table-striped">
									<thead>
										<tr>
											<th>Item Name</th>
											<th>Item Type</th>
											<th>Date Created</th> 
											<th>Date Modified</th>
											<th>Time Modified</th>
											<th>Language</th>
										</tr>
									</thead>
									<tbody>{rows}</tbody>
								  </table>
								  <center><button className="btn btn-info" onClick={this.backToStart}>Back To Start</button></center>
								</div>
							);
			}
		}


//------------------ITEMSLIST FUNCTION-----------------------------//

		class ItemsList extends React.Component {
  			constructor(props) {
  			  super(props);
				this.state= {itemsList: []};
				this.loadItemsFromServer = this.loadItemsFromServer.bind(this);
				this.loadItemsFromServer();
			}

			loadItemsFromServer(props) {
				var self = this;
				$.ajax({
					url: "/text_convert_to_speech/SpeechList",
					type: "GET",
					dataType: "json",
					cache: "true"
				}).then(function (data) {
						self.setState({itemsList: data});
					});
			}

			componentDidMount() {
//				this.loadMembersFromServer();				
			}
			componentWillUnmount() {

			}
			render() {
				return (<ItemsTable itemsList={this.state.itemsList}/>); 
			}
		}

//------------------DOWNLOAD FUNCTION-----------------------------//
		
		class Download extends React.Component {
  			constructor(props) {
  			  super(props);
			  this.backToStart = this.backToStart.bind(this);
			}
			componentDidMount() {

			}
			componentWillUnmount() {

			}
			backToStart(props) {
				ReactDOM.render(
					<Converter />, document.getElementById('root')
				);
			}
			render() {
							
							return (
									<div style={{display: 'none'}}>
						               <iframe src="http://localhost:9090/text_convert_to_speech/download?fileName=a1p2s0f7s9x" />
						           </div>
							);
			}
		}
		
//---------------CONVERTER CLASS-------------------//

		class Converter extends React.Component {
  			constructor(props) {
  			  super(props);
				this.state = {textToConvert: ''};			
				this.handleChange = this.handleChange.bind(this);
    			this.handleSubmit = this.handleSubmit.bind(this);
    			this.postingData = this.postingData.bind(this);
				this.backToStart = this.backToStart.bind(this);
				this.getAudionFile = this.getAudionFile.bind(this);	
  			}

  			handleChange(event) {
		    const target = event.target;
		    const value = target.type === 'checkbox' ? target.checked : target.value;
 		   	const name = target.name;

 		   	this.setState({
 		     	[name]: value
 		   		});
  			}

  			handleSubmit(event) {
//    			alert('A text was submitted: '+ this.state.textToConvert );
				event.preventDefault();
    			this.postingData();
//    			this.getAudionFile();
  			}

  			getAudionFile(props) {
    			alert('audio file '+ this.state.textToConvert +'.wav will download on click of ok');
    			var self = this;
//				$.ajax({
//					url: "/text_convert_to_speech/download?fileName="+this.state.textToConvert,
//					type: "GET",
//					dataType: "json",
//					cache: "true"
//				}).then(function (data) {
//						self.setState({itemsList: data});
//					});
    			return (<Download fileName={this.state.textToConvert}/>);	
  			}

			postingData(props) {
//				alert("posting text to Watson");
				var self = this;
				var jsondata = {
					"text": this.state.textToConvert
				};

			  // Submit form via jQuery/AJAX
			  $.ajax({
				type: "POST",
				contentType: "application/json",
				url: "/text_convert_to_speech",
				data: JSON.stringify(jsondata),
//				dataType: "json",
//				dataType: "text",
		       	cache: false,
		        timeout: 600000,
		        success: function(result){
//		        	window.open('http://localhost:9090/text_convert_to_speech/download?fileName=a1p2s0f7s9x','_blank');
		        	window.open('/text_convert_to_speech/download?fileName='+self.state.textToConvert);
//		        	window.location.href = result;
		        	alert("Speech Audio file Successfully created and ready to download !!! ");
//		        	self.getAudionFile();
		        	
		        },
		        error: function(e) {
//				ReactDOM.render(
//					<StartApp />, document.getElementById('root')
//				);
// 	           	var json = "<h4>Ajax Response</h4><pre>"
//                	+ e.responseText + "</pre>";
				alert("Something went wronge. Please try again later !!!");
 	           	console.log("ERROR : ", e);

 		       	}
			  });
				
			}
			backToStart(props) {
				ReactDOM.render(
					<StartApp />, document.getElementById('root')
				);
			}
			render() {
				return (
					<div className="container" style={{padding:'50px'}}>
						<form onSubmit={this.handleSubmit}>
							<table className="table table-striped">
								<thead>
									<tr>
										<center>
											<div >
												<h3 className="modal-title">
													<label>Package Info</label>
												</h3>
											</div>
										</center>
									</tr>
								</thead>
								<tbody>
									<tr>
										<th>
											<center>
												<div className="form-group">
							            			<div className="row ">
							              				<div className="col-sm-3">
							                				<label for="">Package Id : </label>
							              				</div>
							              				<div className="col-sm-5">
							                				<input className="form-control" name="textToConvert" type="text" value={this.state.textToConvert} onChange={this.handleChange} /> 
														</div>
														<div className="col-sm-4">
															<input className="btn btn-info" type="submit" value="Convert"/>
			              								</div>
			              							</div>
			            						</div>
											</center>
										</th>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
				);
			}

		}

//--------------START PAGE FUNCTION---------------------//

		class StartApp extends React.Component {
  			constructor(props) {
  			  super(props);
			  this.Converter = this.Converter.bind(this);
			  this.SpeechList = this.SpeechList.bind(this);
			}
			componentDidMount() {

			}
			componentWillUnmount() {

			}
			Converter() {
				
				ReactDOM.render(
					<Converter />, document.getElementById('root')
				);
			}

			SpeechList() {
				alert("Fetching Stored Speech Files....");
				ReactDOM.render(
					<ItemsList />, document.getElementById('root')
				);
			}

			render() {
				return (
					<div className="container">
						<table className="table table-striped">
									<thead>
										<tr><center><h1>Welcome to IBM Watson Text to Speech Assitant</h1></center></tr>
										<tr></tr>
									</thead>
						</table>
						<table className="table table-striped">
									<tbody>
										<tr>
											<th><center><button className="btn btn-info" onClick={this.Converter}>Covert Text to Speech</button></center></th>
										</tr>
										<tr>
											<th><center><button className="btn btn-info" onClick={this.SpeechList}>Speech List</button></center></th>
										</tr>
									</tbody>
						</table>
					</div>
				);
			}
		
		}

		ReactDOM.render(
			<Converter />, document.getElementById('root')
		);