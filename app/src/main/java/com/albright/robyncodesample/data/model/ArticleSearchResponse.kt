package com.albright.robyncodesample.data.model

import com.google.gson.annotations.SerializedName

data class ArticleSearchResponse(
    val status: String,
    val copyright: String,
    @SerializedName("num_results") val numResults: Int,
    val results: List<ArticleSnippet>
)

data class ArticleSnippet(
    val uri: String,
    val url: String,
    val id: Int,
    @SerializedName("asset_id") val assetId: Int,
    val source: String,
    @SerializedName("published_date") val publishedDate: String,
    val updated: String,
    val section: String,
    val subsection: String,
    val nytdsection: String,
    @SerializedName("adx_keywords") val adxKeywords: String,
    val column: String,
    val byline: String,
    val type: String,
    val title: String,
    val abstract: String,
    @SerializedName("des_facet") val desFacet: List<String>,
    @SerializedName("org_facet") val orgFacet: List<String>,
    @SerializedName("per_facet") val perFacet: List<String>,
    @SerializedName("geo_facet") val geoFacet: List<String>,
    @SerializedName("media") val media: List<Media>,
    @SerializedName("eta_id") val etaId: Int
)

data class Media(
    val type: String,
    val subtype: String,
    val caption: String,
    val copyright: String,
    @SerializedName("approved_for_syndication") val approvedForSyndication: Int,
    @SerializedName("media-metadata") val mediaMetadata: List<MediaMetadata>
)

data class MediaMetadata(
    val url: String,
    val format: String,
    val height: Int,
    val width: Int
)
