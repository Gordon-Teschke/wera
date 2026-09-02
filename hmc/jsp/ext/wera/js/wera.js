// olaf pump 170531: javascript handler for mouseover image zoom
window.onload_op1=window.onload;
window.onload=function() {
	
console.log(window.location);
	// --- hybris media url
	if ( window.location.hostname == 'hybris4.wera.de' || window.location.hostname == 'localhost' ) {
		var hybrismedia_url	= '//hybris4.wera.de/';
	} else {
		var hybrismedia_url	= '//hybris.wera.de/';
	}

      window.onload_op1();
	  
	  // sj 18-05-07: preview variant image
	  // since the node id of variant image input field changed with each call to a hmc window AND jquery conflicts with built-in libs,
	  // use builtin domQuery lib to have at least a little comfort using dom queries.
	  inputFieldNodes = domQuery("table[title='variant_image - Varianten Bildname'] .stringLayoutChip input.enabled");
	  if ( inputFieldNodes.length > 0 ) {
		  variantImageInputFieldNode = inputFieldNodes[0];
		  console.log("found node for Varianten Bildname!");
		  variantImage = variantImageInputFieldNode.getAttribute('value');
		  if ( variantImage != null ) {
			variantImage = variantImage.trim();
			variantImageLc = variantImage.toLowerCase();
			if ( variantImageLc.match("(\.png|\.jpg|\.gif)$") ) {
				targetNodeForImplanting = variantImageInputFieldNode.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement;
				if ( targetNodeForImplanting.nodeName == 'TD' ) {
					targetNodeForImplanting.insertAdjacentHTML("afterend", "<td style=\"padding-left:10px;\"><img src=\"" + hybrismedia + "sm/"+variantImage+"\"></td>");	
				} else {
					console.log('could not find target node TD! Aborting image injection.');	
				}
			} else {
				console.log("node does not contain a valid image name! Aborting image injection.");
			}
		  }
	  } else {
		console.log("COULD NOT FIND node for Varianten Bildname!");		  
	  }
	  
      for (var i=0, iL=document.images.length, img; i<iL; i++) {
            img=document.images[i];
			isXs = ( img.src.indexOf('/xs/') > -1 );
			isSm = ( img.src.indexOf('/sm/') > -1 );
			if ( isXs || isSm ) {
				img.onmouseover=function(e) {
					this.setAttribute("data-origsrc", this.src);
					if ( this.src.indexOf('/xs/') > -1 ) {
						this.src=this.src.replace( '/xs/', '/md/');	
						this.style.position='absolute';
						if (! this.dataOffsetTop) {
							 this.dataOffsetTop=(e.srcElement) ? this.offsetTop-this.parentNode.offsetTop : 0;
						}
						this.style.top=(e.srcElement) ? this.dataOffsetTop : 0;
						this.style.zIndex=999;
					} else {
						this.src=this.src.replace( '/sm/', '/md/');	
					}
				};
				img.onmouseout=function(e) {
					origSrc = this.getAttribute("data-origsrc");
					if ( ! origSrc ) {
						origSrc = this.src.replace('/md/', '/xs/');
					}
					this.src=origSrc;
					this.style.position='relative';
					this.style.top=0;
					this.style.zIndex=998;
				};
				img.onclick=function() {
					window.open(this.src.replace('/md/', '/orig/'));
				};
			};
		}
		
		// make jalo fields with value starting with http clickable.
		linkedJaloFields = domQuery("input.disabled");
		if ( linkedJaloFields.length > 0 ) {
			for  (var j=0; j<linkedJaloFields.length; j++) {
				jaloValue = linkedJaloFields[j].getAttribute('value');
				if ( jaloValue.startsWith("http://") || jaloValue.startsWith("https://") ) {
					console.log("implementing onclick function on clickable field with value "+jaloValue);
					linkedJaloFields[j].onclick=function() {
						window.open( this.getAttribute('value') );
					}
				}
			}
		}
};

