'use strict';

/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function (config) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.height = 300;

	config.toolbarGroups = [{ name: 'document', groups: ['mode', 'document', 'doctools'] }, { name: 'clipboard', groups: ['clipboard', 'undo'] }, { name: 'editing', groups: ['find', 'selection', 'spellchecker', 'editing'] }, { name: 'forms', groups: ['forms'] }, '/', { name: 'basicstyles', groups: ['basicstyles', 'cleanup'] }, { name: 'paragraph', groups: ['list', 'indent', 'blocks', 'align', 'bidi', 'paragraph'] }, { name: 'links', groups: ['links'] }, { name: 'insert', groups: ['insert'] }, '/', { name: 'styles', groups: ['styles'] }, { name: 'colors', groups: ['colors'] }, { name: 'tools', groups: ['tools'] }, { name: 'others', groups: ['others'] }, { name: 'about', groups: ['about'] }];

	config.extraPlugins = 'uploadimage';
	config.imageUploadUrl = '/api/image/upload';
	config.removeButtons = 'Save,NewPage,Print,Templates,Scayt,Form,Checkbox,Radio,TextField,Textarea,Select,Button,ImageButton,HiddenField,NumberedList,BulletedList,CreateDiv,Blockquote,Anchor,Youtube,PageBreak,SpecialChar,Smiley,HorizontalRule,Table,Flash,Font,FontSize,Styles,About';
};

//# sourceMappingURL=config-compiled.js.map