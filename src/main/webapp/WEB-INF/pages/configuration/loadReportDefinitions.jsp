<%@ include file="/WEB-INF/pages/includes/includes.jsp"%>
<div class="fieldShadow">
	<fieldset>
		<h3>Import Report</h3>
		<form id="upload">			  	
			<div id="errors" class="errors"></div>
			<p>
				<label for="file">File:</label>
				<input type="file" name="file" id="file" data-url="${prefix}/reportdefinition/upload.php" size="24" accept="application/xml" required>
			</p>			
			<div id="formButton">
				<input type="submit" id="submit" value="Upload" class="button">
			</div>					
		</form>
	</fieldset>
</div>
<script>
$j().ready(function () {
	$j('#submit').button();
	$j('#upload').submit(function(event) {
		event.preventDefault();
	});
	$j('#file').fileupload(
	{
		dataType : 'json',
		done : function(e, data) {
			$j('#errors').html('The file was successfully uploaded');
			setTimeout(function(){
				$j('#errors').html("File upload successful");
				KWTRDI.ajaxLoad('/reportdefinition/upload');
			},2000);
		}
	});
});
</script>