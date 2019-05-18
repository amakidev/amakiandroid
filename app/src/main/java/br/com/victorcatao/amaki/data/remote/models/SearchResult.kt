package br.com.victorcatao.amaki.data.remote.models

import java.io.Serializable

data class SearchResult(
        var result: List<SearchModel>
): Serializable

