<!--
    SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
    
    SPDX-License-Identifier: AGPL-3.0-or-later
-->

<template>
    <div style="min-height: 1000px">
        <div v-if="component && config" class="pb-5">
            <div class="bg-light" style="display: flex; flex-direction: column">
                <div class="container-fluid container-extended pb-0 pt-3 pt-sm-5 pl-4 pr-4">

                    <div class="row">
                        <div class="col-lg-8 d-flex justify-content-between flex-column flex-sm-row w-100 pl-0">
                            <div style="margin-right: 35px; min-width: 70px; min-height: 35px" class="mb-3">
                                <nuxt-link :to="returnLink" class="btn-circle arrow-left filled-dark return-button">
                                    <img src="/icons/ic_arrow.svg" class="return-icon" />
                                </nuxt-link>
                            </div>
                            <div style="flex-grow: 1">
                                <h1>#{{ component.title }}</h1>
                                <div class="d-flex">
                                    <div class="full-width mr-2">
                                        <div>
                                        {{ component.descriptionAbstract }}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 justify-content-between ">
                            <div class="d-flex flex-row pt-3">
                                <div class="performance-col">
                                    <circular-chart :circle-value="versionSizeChartPercent" :color="versionSizeChartColor"></circular-chart>
                                    <div>
                                        Size <span class="font-weight-bold">{{ versionSizeKb }}</span> (kB)
                                    </div>
                                </div>
                                <div class="performance-col">
                                    <circular-chart :circle-value="mobileRating" :color="mobileRatingChartColor">
                                        <div class="d-flex justify-content-center align-items-center" style="width: 36px; height: 36px">
                                            {{ mobileRating }}
                                        </div>
                                    </circular-chart>
                                    <a :href="pageSpeedInsightUrl" target="_blank" class="text-underline">
                                        Mobile perf.
                                    </a>
                                </div>

                                <div class="performance-col">
                                    <circular-chart :circle-value="desktopRating" :color="desktopRatingChartColor">
                                        <div class="d-flex justify-content-center align-items-center" style="width: 36px; height: 36px">
                                            {{ desktopRating }}
                                        </div>
                                    </circular-chart>
                                    <a :href="pageSpeedInsightUrl" target="_blank" class="text-underline">
                                        Desktop perf.
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-12 d-flex flex-column flex-md-row w-100 p-0 pt-5 justify-content-end">
                            <div class="d-flex flex-row tab-buttons wc-tabs">
                                <div class="text-uppercase tab-button" @click="changeTabIndex(0)" :class="(tabIndex == 0) ? 'active' : null">
                                    configuration
                                </div>
                                <div class="text-uppercase tab-button" @click="changeTabIndex(1)" :class="(tabIndex == 1) ? 'active' : null">
                                    share
                                </div>
                                <div class="text-uppercase tab-button" @click="changeTabIndex(2)" :class="(tabIndex == 2) ? 'active' : null">
                                    about
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

                <div v-if="showPreview && hasAnyVersion">
                    <div class="container-fluid container-extended pb-4 p-2 pr-4 pt-sm-2">
                        <div class="detail-content-left">
                            <b-alert
                            :show="!isLatestVersionActive"
                            variant="danger"
                            class="mt-4 mb-4 detail-content-left"
                            >You have not selected the latest version of this
                            webcomponent.</b-alert
                            >
                        </div>
                    </div>
                    <div class="container-fluid container-extended pb-4 p-2 pl-4 pr-4 pt-sm-2">
                        <div class="row">
                            <div class="detail-content-left" :class="{'col-lg-8 ': !collapsedRightSidebar, 'col-lg-12 ':collapsedRightSidebar}">
                                <b-card id="widget-preview" class="full-height">
                                    <b-card-text id="twrap" class="text-center">
                                        <iframe
                                            id="tframe"
                                            class="full-height full-width"
                                            style="min-height: 800px"
                                            title="iframe-preview"
                                        ></iframe>
                                    </b-card-text>
                                </b-card>
                            </div>
                            <div id="chooseRightSidebarTab" class="config-tabs col-lg-4 pt-3 pt-lg-0 detail-content-right">
                                <div v-if="!collapsedRightSidebar" > 
                                    
                                    <div v-show="tabIndex == 1" title="SHARE">
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <h4>SHARE</h4>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <b-card class="code-container white">
                                                    <b-card-text>
                                                        <prism-editor v-model="getDetailsLink" class="my-editor" :highlight="highlighter" style="border: 0; background-color: inherit"/>
                                                    </b-card-text>
                                                </b-card>
                                                <div @click="copyLinkToClipboard" id="details-copy-details-link" class="simple-btn btn btn-primary">
                                                    Copy details link
                                                </div>
                                            </div>
                                            <div class="col-lg-12 pt-3">
                                                <b-card class="code-container white">
                                                    <b-card-text>
                                                        <prism-editor v-model="getFullscreenLink" class="my-editor" :highlight="highlighter" style="border: 0; background-color: inherit"/>
                                                    </b-card-text>
                                                </b-card>
                                                <div @click="copyFullscreenLinkToClipboard" id="details-copy-fullscreen-link" class="simple-btn btn btn-primary">
                                                    Copy fullscreen link
                                                </div>
                                            </div>                                        
                                        </div>
                                        <b-popover
                                            id="popover"
                                            target="details-copy-details-link"
                                            :show.sync="showPopover"
                                            triggers="click"
                                            placement="top"
                                        >
                                            Copied to clipboard
                                        </b-popover>
    
                                        <b-popover
                                            id="popover"
                                            target="details-copy-fullscreen-link"
                                            :show.sync="showFullscreenPopover"
                                            triggers="click"
                                            placement="top"
                                        >
                                            Copied to clipboard
                                        </b-popover>
    
                                        <div class="row" id="chooseRightSidebarTabEmbed">
                                            <div class="col-lg-12 pt-5">
                                                <h4>EMBED</h4>
                                            </div>
                                        </div>
                                        <div>
                                            <!-- <div class="text-uppercase font-weight-bold mb-2">
                                                copy code
                                            </div> -->
                                            <b-card id="widget-codesnippet" class="white" style="min-height: 250px">
                                                <b-card-text>
                                                    <prism-editor v-model="editorCode" class="my-editor" :highlight="highlighter" style="border: 0; background-color: inherit" @blur="updateSnippetFromEditor"/>
                                                </b-card-text>
                                                
                                                <div v-if="showDiscard" slot="footer" class="text-right text-uppercase">
                                                    <span style="cursor: pointer" class="mr-4" @click="resetEditorSnippet()">
                                                        DISCARD CHANGES
                                                    </span>
                                                </div>
                                            </b-card>
                                            <b-alert class="mt-2" variant="info" :show="showDiscard">
                                                This is a custom snippet and cannot be loaded into the EASY CONFIGURATION.
                                            </b-alert>
    
                                            <div @click="copySnippetToClipboard" id="details-copy-embed-link" class="simple-btn btn btn-primary">
                                                Copy embed code
                                            </div>
                                            <b-popover
                                                id="popover"
                                                target="details-copy-embed-link"
                                                :show.sync="showEmbedPopover"
                                                triggers="click"
                                                placement="top"
                                            >
                                                Copied to clipboard
                                            </b-popover>
    
                                        </div>
                                    </div>
                                    <div v-show="tabIndex == 2" title="ABOUT">
                                        <h4>ABOUT</h4>
                                        <WcDetailBlock :return-link="returnLink"></WcDetailBlock>
                                    </div>
                                    <div v-show="tabIndex != 1 && tabIndex != 2" title="CONFIGURATION">
                                        <h4>CONFIGURATION</h4>
                                        <div class="version-select-container ml-md-5">
                                            <b-form-select :value="selectedVersion" class="version-select ml-md-2" @change="reloadWebcomponentAtVersion">
                                                <option v-for="version in component.versions" :key="version.versionTag" >
                                                    {{ version.versionTag }}
                                                </option>
                                            </b-form-select>
                                        </div>
                                        <div id="first-tab" title="EASY CONFIGURATION" :active="initTabOne">
                                            <div v-show="!editMode">
                                                <div class="full-height widget-config">
                                                    <span>
                                                        <b-checkbox v-model="autoUpdate" class="d-inline-block"></b-checkbox>auto update
                                                    </span>
                                                    <b-card-text>
                                                        <WCSConfigTool v-if="config" :config="config.configuration" :restore-snippet="snippet" @snippet="updateSnippetFromTool"></WCSConfigTool>
                                                    </b-card-text>
                                                </div>
                                            </div>
                                            <div v-show="editMode">
                                                <div class="text-uppercase font-weight-bold mb-2">
                                                    configuration
                                                </div>
                                                <b-card class="full-height widget-config">
                                                    <b-card-text>
                                                        <strong>The selected configuration is not compatible with our configuration tool.</strong><br />
                                                        You can either switch over to the "EDIT CODE" tab or "DISCARD CHANGES".
                                                    </b-card-text>
                                                    
                                                    <div slot="footer" class="text-right text-uppercase">
                                                        <span style="cursor: pointer" class="mr-4" @click="resetEditorSnippet()">
                                                            DISCARD CHANGES
                                                        </span>
                                                    </div>
                                                </b-card>
                                            </div>
                                        </div>
                                    </div>
    
                                    
                                </div>
                            </div>
                        </div>
                    </div>
                                                            
                    <detail-bottom-bar @updatePreview="forceUpdatePreview" @copyCode="copySnippetToClipboard" @changeTabIndex="changeTabIndex"></detail-bottom-bar>
                    
                </div>
                
                <div v-else>
                    <component-read-me />
                </div>
            </div>
        </div>
    </template>
    
    <script lang="ts">
    import Vue from 'vue';
    import WCSConfigTool from 'odh-web-components-configurator/src/components/wcs-configurator.vue';
    import { PrismEditor } from 'vue-prism\-editor';
    import WcDetailBlock from '../../../../components/webcomponent/WcDetailBlock.vue';
    import ComponentReadMe from '~/components/webcomponent/ComponentReadMe.vue';

    import CircularChart from '~/components/circular-chart.vue';
    import { WebcomponentVersionModel } from '~/domain/model/WebcomponentVersionModel';

    // eslint-disable-next-line import/order
    import { highlight, languages } from 'prismjs/components/prism-core';
    import 'prismjs/components/prism-clike';
    import 'prismjs/components/prism-markup.min';
    
    import DetailBottomBar from '~/components/detail-bottom-bar.vue';
    import { copyToClipboard } from '~/utils/ClipboardUtils';
    import { WebcomponentModel } from '~/domain/model/WebcomponentModel';
    import { WebcomponentConfigurationModel } from '~/domain/model/WebcomponentConfigurationModel';
    
    export default Vue.extend({
        components: {
            DetailBottomBar,
            ComponentReadMe,
            WcDetailBlock,
            WCSConfigTool,
            PrismEditor,
            CircularChart
        },
        data() {
            return {
                previewBaseURL: (this as any).$api.baseUrl,
                autoUpdate: true,
                showPreview: true,
                editorCode: '',
                initTabOne: false,
                timer: null,
                tabIndex: 0,
                collapsedRightSidebar:false,
                showPopover: false,
                showFullscreenPopover: false,
                showEmbedPopover: false,
                intervalId: 0,
            };
        },
        
        async fetch() {
            this.$store.commit('webcomponent/SET_EDIT_MODE', true);
            
            await this.$store.dispatch('webcomponent/loadWebcomponent', {
                uuid: this.$route.params.id,
                version: this.$route.params.version,
            });
            
            if (!this.snippet) {
                this.$store.commit('webcomponent/SET_EDIT_MODE', false);
            }
        },
        
        computed: {
            getDetailsLink() {
                if (this.version) {
                    return window.location.origin+this.localePath({
                        name: 'webcomponent-id-version',
                        params: {
                            id: this.component.shortName ? this.component.shortName : this.component.uuid,
                            version: this.selectedVersion
                        },
                        query: { from: this.returnTo },
                    });
                }else{
                    return window.location.origin+this.localePath({
                        name: 'webcomponent-id',
                        params: {
                            id: this.component.shortName ? this.component.shortName : this.component.uuid,
                        },
                        query: { from: this.returnTo },
                    });
                }
            },
            getFullscreenLink() {
                if (this.version) {
                    return window.location.origin+this.localePath({
                        name: 'webcomponent-id-version-fullscreen',
                        params: { 
                            id: this.component.shortName ? this.component.shortName : this.component.uuid,
                            version: this.selectedVersion
                        },
                        query: { attribs: this.attribs },
                    });
                } else {
                    return window.location.origin+this.localePath({
                        name: 'webcomponent-id-fullscreen',
                        params: { 
                            id: this.component.shortName ? this.component.shortName : this.component.uuid
                        },
                        query: { attribs: this.attribs },
                    });
                }
            },
            currentVersionData(): WebcomponentVersionModel {
                return this.$store.getters['webcomponent/currentVersionData'];
            },
            mobileRatingChartColor(): string {
                if (this.mobileRating < 80) {
                    return 'red';
                }

                return 'green';
            },
            desktopRatingChartColor(): string {
                if (this.desktopRating < 80) {
                    return 'red';
                }

                return 'green';
            },
            versionSizeChartColor(): string {
                if (this.versionSizeChartPercent > 80) {
                    return 'red';
                }

                return 'green';
            },
            versionSizeChartPercent(): number {
                return Math.ceil((100 * Math.min(500, this.versionSizeKb)) / 500);
            },
            mobileRating(): number {
                if (!this.currentVersionData) {
                    return 0;
                }
                return this.currentVersionData.lighthouseMobilePerformanceRating;
            },
            desktopRating(): number {
                if (!this.currentVersionData) {
                    return 0;
                }
                return this.currentVersionData.lighthouseDesktopPerformanceRating;
            },
            versionSizeKb(): number {
                if (!this.currentVersionData) {
                    return 0;
                }
                return this.currentVersionData.distSizeTotalKb;
            },
            pageSpeedInsightUrl(): string {
                return (
                    'https://developers.google.com/speed/pagespeed/insights/?url=' +
                    this.externalPreviewBaseUrl
                );
            },


            showDiscard(): boolean {
                return this.snippet !== this.snippetFromTool;
            },
            hasAnyVersion(): boolean {
                return !!this.$store.state.webcomponent.versionTag;
            },
            returnLink(): string {
                if (this.$route.query.from) {
                    return this.$route.query.from;
                }
                
                return this.localePath('index');
            },
            isLatestVersionActive(): boolean {
                if (!this.component) {
                    return false;
                }
                return (
                this.selectedVersion === 'latest' ||
                this.selectedVersion === this.component.versions[1].versionTag
                );
            },
            component(): WebcomponentModel {
                return this.$store.state.webcomponent.webcomponent;
            },
            config(): WebcomponentConfigurationModel {
                return this.$store.state.webcomponent.configuration;
            },
            selectedVersion(): string {
                return this.$store.state.webcomponent.versionTag;
            },
            snippetFromTool(): string {
                return this.$store.state.webcomponent.snippetFromTool;
            },
            snippet(): string {
                return this.$store.state.webcomponent.snippet;
            },
            editMode() {
                return this.editorCode && this.editorCode !== this.snippetFromTool;
            },
            externalPreviewUrl(): string {
                if (!this.component || !this.config) {
                    return '';
                }
                return (
                this.previewBaseURL +
                '/preview/' +
                this.component.uuid +
                '/' +
                this.selectedVersion +
                '?attribs=' +
                this.$store.getters['webcomponent/transportString']
                );
            },
        },
        
        watch: {
            snippet(value) {
                this.editorCode = value;
            },
            externalPreviewUrl(url) {
                if (url) {
                    this.updatePreview();
                }
            },
        },
        
        mounted() {
            this.resetEditorSnippet();
            
            if (this.externalPreviewUrl) {
                this.updatePreview();
            }
            if (!this.editorCode) {
                this.editorCode = this.snippet;
            }
            
            this.initTabOne = !this.editMode;
            this.$store.commit('webcomponent/SET_EDIT_MODE', this.editMode);

            if(this.$route.hash == '#chooseRightSidebarTabEmbed'){
                this.handleScroll(1)
            }
        },
        
        methods: {
            handleScroll(tabIndex) {
                this.changeTabIndex(tabIndex,true)
                this.scroll(0);
            },
            scroll(loops){
                if(loops > 100){
                    return;
                }
                const anchor = document.getElementById(`chooseRightSidebarTabEmbed`)
                setTimeout(()=>{
                    console.log("anchor anchor",anchor,loops)
                    if (anchor) {
                        console.log("dadasdasdassaaa asdas asd asd as",window.pageYOffset)
                        window.scrollTo({
                            top: anchor.getBoundingClientRect().top + window.pageYOffset -50
                        })
                    }else{
                        loops += 1;
                        this.scroll(loops)
                    }
                },100);
            },
            collapseRightSidebar(){
                this.tabIndex = null;
                this.collapsedRightSidebar = true;
            },
            changeTabIndex(tIndex, notCollapsable = false){
                if(this.tabIndex === tIndex && !notCollapsable){
                    if(this.collapsedRightSidebar){
                        this.tabIndex = tIndex;
                        this.collapsedRightSidebar = false;
                    }else{
                        this.collapseRightSidebar()
                    }
                }else{
                    this.collapsedRightSidebar = false;
                    this.tabIndex = tIndex;
                }

            },
            async reloadWebcomponentAtVersion(version: string) {
                await this.$router.push(
                this.localePath({
                    name: 'webcomponent-id-version',
                    params: { id: this.$route.params.id, version },
                })
                );
                
                setTimeout(() => {
                    location.reload();
                }, 100);
            },
            
            updateSnippetFromTool(snippet: string) {
                this.$store.commit('webcomponent/SET_SNIPPET_FROM_TOOL', snippet);
            },
            
            updateSnippetFromEditor() {
                if (this.editorCode === this.snippet) {
                    return;
                }
                
                this.$store.commit(
                'webcomponent/SET_SNIPPET_FROM_EDITOR',
                this.editorCode
                );
            },
            
            highlighter(code: string): string {
                return highlight(code, languages.markup, 'markup'); // returns html
            },
                        
            resetEditorSnippet(): void {
                this.$store.dispatch('webcomponent/resetSnippet');
                this.tabIndex = 0;
            },
            
            copyLinkToClipboard(): void {
                copyToClipboard(this.getDetailsLink);
                clearInterval(this.intervalId);
                this.showPopover = true;
                this.intervalId = setInterval(
                    function () {
                    this.showPopover = false;
                    }.bind(this),
                    3000
                );
            },

            copyFullscreenLinkToClipboard(): void {
                copyToClipboard(this.getFullscreenLink);
                clearInterval(this.intervalId);
                this.showFullscreenPopover = true;
                this.intervalId = setInterval(
                    function () {
                    this.showFullscreenPopover = false;
                    }.bind(this),
                    3000
                );
            },
            copySnippetToClipboard(): void {
                copyToClipboard(this.editorCode);
                clearInterval(this.intervalId);
                this.showEmbedPopover = true;
                this.intervalId = setInterval(
                    function () {
                    this.showEmbedPopover = false;
                    }.bind(this),
                    3000
                );
            },
            
            forceUpdatePreview() {
                this.updateSnippetFromEditor();
                this.updatePreview(true);
            },
            
            updatePreview(force = false): void {
                const src = this.externalPreviewUrl;
                if (!src) {
                    return; // probably not yet loaded
                }
                
                if (!force && !this.autoUpdate) {
                    return;
                }
                
                const oldElement = document.getElementById('tframe');
                
                if (!oldElement) {
                    return;
                }
                
                oldElement.parentNode.removeChild(oldElement);
                
                const newElement = document.createElement('iframe');
                newElement.setAttribute('id', 'tframe');
                newElement.setAttribute('class', 'full-height full-width');
                newElement.setAttribute('style', 'min-height: 800px;');
                newElement.setAttribute('frameborder', '0');
                
                document.getElementById('twrap').appendChild(newElement);
                newElement.src = src;
                
                // newElement.contentDocument.write(this.effectiveSnippet);
                newElement.contentDocument.close();
            },
        },
    });
</script>

<style lang="scss">
.wc-tabs{
    .tab-button{
        border-bottom:none;
        opacity: 0.8;
        
        &.active{
            border-bottom: 2px solid black;
            opacity: 1;
        }
    }
}
.simple-btn{
    &.btn::after{
        content: none;
    }
}
.widget-config {
    .card-footer {
        background-color: inherit;
    }
}

#widget-codesnippet {
    .card-body {
        background-color: #fafafa;
    }
    
    &.white .card-body {
        background-color: white;
    }
    
    .card-footer {
        background-color: inherit;
    }
}



#widget-preview {
    .card-footer {
        background-color: inherit;
    }
}

.d-table {
    border-collapse: separate;
    border-spacing: 0 0.5rem;
}

.version-select {
    padding-right: 8px !important;
    max-width: 150px;
}

.version-select-container {
    display: flex;
    flex-direction: row;
    flex-grow: 1;
    justify-content: flex-end;
    padding-left: 15px;
}

.my-editor {
    /* we dont use `language-` classes anymore so thats why we need to add background and text color manually */
    background: #2d2d2d;
    color: #ccc;
    
    /* you must provide font-family font-size line-height. Example: */
    font-family: Fira code, Fira Mono, Consolas, Menlo, Courier, monospace;
    font-size: 14px;
    line-height: 1.5;
    padding: 5px;
}

/* optional class for removing the outline */
.prism-editor__textarea:focus {
    outline: none;
}

@media (max-width: 576px) {
    .version-select {
        width: 100%;
        min-width: 100%;
    }
    
    .version-select-container {
        padding-left: 0;
        width: 100%;
        min-width: 100%;
    }
}

footer .bg-light {
    padding-bottom: 30px;
}
</style>
